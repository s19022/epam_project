package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.*;
import com.example.InspectionBoard.model.dao.*;
import com.example.InspectionBoard.model.dto.ChangeFacultyRegistrationStatusDto;
import com.example.InspectionBoard.model.dto.SaveFacultyRegistrationDto;
import com.example.InspectionBoard.model.dto.db.*;
import com.example.InspectionBoard.model.entity.FacultyRegistration;
import com.example.InspectionBoard.model.enums.AccountRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.example.InspectionBoard.Constants.SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE;
import static com.example.InspectionBoard.model.dto.db.Mapper.toFacultyRegistration;

public class FacultyRegistrationService {
    private static final Logger LOGGER = LogManager.getLogger(FacultyRegistrationService.class.getName());

    /**
     *
     * @param login Enrollee login
     * @return all faculties that Enrollee with given login is registered to
     */
    public List<FacultyRegistration> findByEnrolleeLogin(String login){
        try(FacultyRegistrationDao dao = DaoFactory.getInstance().createFacultyRegistrationDao()){
            return toFacultyRegistration(dao.findByEnrolleeLogin(login));
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    /**
     *
     * @return all FacultyRegistration with status equals to PENDING
     */
    public List<FacultyRegistration> findAllPending(){
        try(FacultyRegistrationDao dao = DaoFactory.getInstance().createFacultyRegistrationDao()){
            return toFacultyRegistration(dao.findAllPending());
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    /**
     *
     * @param dto information about facultyRegistration to be updated
     * @throws NotEnoughPlacesException if faculty with given name doesn't have free places
     * @throws NoSuchFacultyException if there are no faculty with given name
     */
    public void changeStatus(ChangeFacultyRegistrationStatusDto dto) throws NotEnoughPlacesException, NoSuchFacultyException {
        DaoFactory factory = DaoFactory.getInstance();
        try(
                Connection connection = factory.getConnection();
                FacultyRegistrationDao facultyRegistrationDao = factory.createFacultyRegistrationDao(connection);
                FacultyDao facultyDao = factory.createFacultyDao(connection)
        ){
            connection.setAutoCommit(false);
            try{
                changeStatus(dto, facultyRegistrationDao, facultyDao);
            }catch (Exception ex){
                connection.rollback();
                throw ex;
            }finally {
                connection.setAutoCommit(true);
            }
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    /**
     *  saves Enrollee with given login to faculty with given name
     * @param accountLogin account Login
     * @param facultyName faculty name
     * @throws NoSuchAccountException if Account with given login doesn't exist in database
     * @throws NoSuchFacultyException if Faculty with given name doesn't exist in database
     * @throws CannotRegisterToFacultyException if Enrollee doesn't meet requirements for given faculty
     * @throws AlreadyRegisteredException if Enrollee already saved to given faculty
     */
    public void register(String accountLogin, String facultyName)
            throws NoSuchAccountException, NoSuchFacultyException, CannotRegisterToFacultyException, AlreadyRegisteredException {
        DaoFactory factory = DaoFactory.getInstance();
        try(
                Connection connection = factory.getConnection();
                EnrolleeSubjectDao enrolleeSubjectDao = factory.createEnrolleeSubjectDao(connection);
                RequiredSubjectDao requiredSubjectDao = factory.createRequiredSubjectDao(connection);
                FacultyRegistrationDao facultyRegistrationDao = factory.createFacultyRegistrationDao(connection);
                AccountDao accountDao = factory.createAccountDao(connection);
                FacultyDao facultyDao = factory.createFacultyDao(connection)
            ){
            connection.setAutoCommit(false);
            try{
                int enrolleeId = getEnrolleeId(accountDao, accountLogin);
                int facultyId = getFacultyId(facultyDao, facultyName);

                List<DbRequiredSubjectDto> requiredSubjects = requiredSubjectDao.getAllByFacultyId(facultyId);
                List<DbEnrolleeSubjectDto> enrolleeSubjects = enrolleeSubjectDao.getAllByEnrolleeId(enrolleeId);

                if (!canRegister(requiredSubjects, enrolleeSubjects)){
                    throw new CannotRegisterToFacultyException();
                }
                saveRegistration(facultyRegistrationDao, enrolleeId, facultyId);
            }catch (Exception ex) {
                connection.rollback();
                throw ex;
            }finally{
                connection.setAutoCommit(true);
            }
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    /**
     *
     * @param dao dao to interact with database
     * @param login enrollee login
     * @return id of enrollee with given id
     * @throws SQLException is thrown by dao
     * @throws NoSuchAccountException if database doesn't have enrollee with given login
     */
    private static int getEnrolleeId(AccountDao dao, String login) throws SQLException, NoSuchAccountException {
        DbAccountDto account = dao.findByLogin(login).orElseThrow(NoSuchAccountException::new);
        if (account.getRole() != AccountRole.ENROLLEE) {
            throw new NoSuchAccountException();
        }
        return account.getId();
    }

    /**
     *
     * @param dao to interact with database
     * @param facultyName name of the faculty
     * @return id of the faculty with given name
     * @throws SQLException is thrown by dao
     * @throws NoSuchFacultyException if database doesn't have faculty with given name
     */
    private static int getFacultyId(FacultyDao dao, String facultyName) throws SQLException, NoSuchFacultyException {
        DbFacultyDto faculty = dao.findByName(facultyName).orElseThrow(NoSuchFacultyException::new);
        return faculty.getId();
    }

    /**
     *  Compares required marks and subjects of faculty to data passed by enrollee
     * @param requiredSubjects subjects required by faculty
     * @param enrolleeSubjects subjects passed by enrollee
     * @return whether enrollee meets requirements to register to faculty
     */
    private static boolean canRegister(List<DbRequiredSubjectDto> requiredSubjects, List<DbEnrolleeSubjectDto> enrolleeSubjects){
        for (DbRequiredSubjectDto subject : requiredSubjects){
            boolean contains = enrolleeSubjects.stream().anyMatch(s -> subject.getId() == s.getId() && subject.getMinimalGrade() <= s.getMark());
            if (!contains){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param dao to interact with database
     * @param enrolleeId id of enrollee to be saved
     * @param facultyId id of faculty to be saved
     * @throws AlreadyRegisteredException if there is a registration with given enrolleeId and facultyId
     * @throws SQLException is thrown by dao
     */
    private static void saveRegistration(FacultyRegistrationDao dao, int enrolleeId, int facultyId)
            throws AlreadyRegisteredException, SQLException {
        try{
            dao.save(new SaveFacultyRegistrationDto(enrolleeId, facultyId));
        }catch (SQLException ex){
            if(SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE.equals(ex.getSQLState())){
                throw new AlreadyRegisteredException(ex);
            }
            throw ex;
        }
    }

    /**
     *
     * @param dto data to be saved
     * @param facultyRegistrationDao dao to interact with facultyRegistration
     * @param facultyDao dao to interact with faculty
     * @throws SQLException is thrown by dao
     * @throws NotEnoughPlacesException if faculty with given name doesn't have free places
     * @throws NoSuchFacultyException if faculty with given name doesn't exist
     */
    private void changeStatus(ChangeFacultyRegistrationStatusDto dto, FacultyRegistrationDao facultyRegistrationDao, FacultyDao facultyDao) throws SQLException, NotEnoughPlacesException, NoSuchFacultyException {
        switch (dto.getNewStatus()){
            case PENDING:
            case REJECTED:
                facultyRegistrationDao.changeStatus(dto);
                return;
            case ACCEPTED_CONTRACT:
                changeStatusToContract(dto, facultyRegistrationDao, facultyDao);
                return;
            case ACCEPTED_BUDGET:
                changeStatusToBudget(dto, facultyRegistrationDao, facultyDao);
        }
    }

    /**
     *
     * @param dto data to be saved
     * @param facultyRegistrationDao dao to interact with facultyRegistration
     * @param facultyDao dao to interact with faculty
     * @throws SQLException is thrown by dao
     * @throws NotEnoughPlacesException if faculty with given name doesn't have free places
     * @throws NoSuchFacultyException if faculty with given name doesn't exist
     */
    private void changeStatusToContract(ChangeFacultyRegistrationStatusDto dto, FacultyRegistrationDao facultyRegistrationDao, FacultyDao facultyDao) throws SQLException, NoSuchFacultyException, NotEnoughPlacesException {
        DbFacultyDto facultyDto = facultyDao.findByName(dto.getFacultyName()).orElseThrow(NoSuchFacultyException::new);
        if ((facultyDto.getAllPlaces() - facultyDto.getBudgetPlaces()) <= 0){
            throw new NotEnoughPlacesException();
        }
        facultyDao.subtractContractPlace(dto.getFacultyName());
        facultyRegistrationDao.changeStatus(dto);
    }


    /**
     *
     * @param dto data to be saved
     * @param facultyRegistrationDao dao to interact with facultyRegistration
     * @param facultyDao dao to interact with faculty
     * @throws SQLException is thrown by dao
     * @throws NotEnoughPlacesException if faculty with given name doesn't have free places
     * @throws NoSuchFacultyException if faculty with given name doesn't exist
     */
    private void changeStatusToBudget(ChangeFacultyRegistrationStatusDto dto, FacultyRegistrationDao facultyRegistrationDao, FacultyDao facultyDao) throws SQLException, NoSuchFacultyException, NotEnoughPlacesException {
        DbFacultyDto facultyDto = facultyDao.findByName(dto.getFacultyName()).orElseThrow(NoSuchFacultyException::new);
        if (facultyDto.getBudgetPlaces() <= 0){
            throw new NotEnoughPlacesException();
        }
        facultyDao.subtractBudgetPlace(dto.getFacultyName());
        facultyRegistrationDao.changeStatus(dto);
    }
}
