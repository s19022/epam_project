package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.*;
import com.example.InspectionBoard.model.dao.*;
import com.example.InspectionBoard.model.dto.SaveFacultyRegistrationDto;
import com.example.InspectionBoard.model.dto.db.*;
import com.example.InspectionBoard.model.enums.AccountRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FacultyRegistrationService {
    private static final Logger LOGGER = LogManager.getLogger(FacultyRegistrationService.class.getName());
    private static final String SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE = "23505";

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

    private static int getEnrolleeId(AccountDao dao, String login) throws SQLException, NoSuchAccountException {
        DbAccountDto account = dao.findByLogin(login).orElseThrow(NoSuchAccountException::new);
        if (account.getRole() != AccountRole.ENROLLEE) {
            throw new NoSuchAccountException();
        }
        return account.getId();
    }

    private static int getFacultyId(FacultyDao dao, String facultyName) throws SQLException, NoSuchFacultyException {
        DbFacultyDto faculty = dao.findByName(facultyName).orElseThrow(NoSuchFacultyException::new);
        return faculty.getId();
    }

    private static boolean canRegister(List<DbRequiredSubjectDto> requiredSubjects, List<DbEnrolleeSubjectDto> enrolleeSubjects){
        for (DbRequiredSubjectDto subject : requiredSubjects){
            boolean contains = enrolleeSubjects.stream().anyMatch(s -> subject.getId() == s.getId() && subject.getMinimalGrade() <= s.getMark());
            if (!contains){
                return false;
            }
        }
        return true;
    }

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
}
