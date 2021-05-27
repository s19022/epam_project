package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.CannotRegisterToFacultyException;
import com.example.InspectionBoard.exceptions.NoSuchAccountException;
import com.example.InspectionBoard.exceptions.NoSuchFacultyException;
import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.*;
import com.example.InspectionBoard.model.dto.parse.FacultyRegistrationDto;
import com.example.InspectionBoard.model.dto.parse.ParseAccountDto;
import com.example.InspectionBoard.model.dto.parse.ParseEnrolleeSubjectDto;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.entity.RequiredSubject;
import com.example.InspectionBoard.model.enums.AccountRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FacultyRegistrationService {
    private static final Logger LOGGER = LogManager.getLogger(FacultyRegistrationService.class.getName());

    public static void register(String accountLogin, String facultyName)
            throws NoSuchAccountException, NoSuchFacultyException, CannotRegisterToFacultyException {
        try(
                EnrolleeSubjectDao enrolleeSubjectDao = DaoFactory.getInstance().createEnrolleeSubjectDao();
                Connection connection = enrolleeSubjectDao.getConnection();
                RequiredSubjectDao requiredSubjectDao = DaoFactory.getInstance().createRequiredSubjectDao(connection);
                FacultyRegistrationDao registrationDao = DaoFactory.getInstance().createFacultyRegistrationDao(connection)
            ){
            try{
                enrolleeSubjectDao.getConnection().setAutoCommit(false);
                int enrolleeId = getEnrolleeId(connection, accountLogin);
                int facultyId = getFacultyId(connection, facultyName);

                List<RequiredSubject> requiredSubjects = requiredSubjectDao.getAllByFacultyId(facultyId);
                List<ParseEnrolleeSubjectDto> enrolleeSubjects = enrolleeSubjectDao.getAllByEnrolleeId(enrolleeId);

                if (!canRegister(requiredSubjects, enrolleeSubjects)){
                    throw new CannotRegisterToFacultyException();
                }
                registrationDao.register(new FacultyRegistrationDto(enrolleeId, facultyId));
            }catch (Exception ex) {
                connection.rollback();
                throw ex;
            }finally{
                enrolleeSubjectDao.getConnection().setAutoCommit(true);
            }
        }catch (SQLException ex){
            LOGGER.error(ex);
            ex.printStackTrace();
            throw new SQLExceptionWrapper(ex);
        }
    }

    private static int getEnrolleeId(Connection connection, String login) throws NoSuchAccountException, SQLException {
        AccountDao accountDao = DaoFactory.getInstance().createAccountDao(connection);
        LOGGER.info(login);
        ParseAccountDto account = accountDao.findByLogin(login).orElseThrow(NoSuchAccountException::new);
        if (account.getRole() != AccountRole.USER) {
            throw new NoSuchAccountException();
        }
        return account.getId();
    }

    private static int getFacultyId(Connection connection, String facultyName) throws SQLException, NoSuchFacultyException {
        FacultyDao dao = DaoFactory.getInstance().createFacultyDao(connection);
        Faculty faculty = dao.getByName(facultyName).orElseThrow(NoSuchFacultyException::new);
        return faculty.getId();
    }

    private static boolean canRegister(List<RequiredSubject> requiredSubjects, List<ParseEnrolleeSubjectDto> enrolleeSubjects){
        for (RequiredSubject subject : requiredSubjects){
            boolean contains = enrolleeSubjects.stream().anyMatch(s -> subject.getSubject().getId() == s.getId() && subject.getMinimalGrade() < s.getMark());
            if (!contains){
                return false;
            }
        }
        return true;
    }
}
