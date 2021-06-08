package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.MarkIsNotValidException;
import com.example.InspectionBoard.exceptions.NoSuchSubjectException;
import com.example.InspectionBoard.exceptions.NotUniqueSubjectException;
import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.EnrolleeSubjectDao;
import com.example.InspectionBoard.model.dto.CreateEnrolleeSubjectDto;
import com.example.InspectionBoard.model.entity.EnrolleeSubject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.model.dto.db.Mapper.toEnrolleeSubject;
import static com.example.InspectionBoard.model.service.ServiceUtility.isMarkValid;

public class EnrolleeSubjectService {
    private static final Logger LOGGER = LogManager.getLogger(EnrolleeSubjectService.class.getName());

    /**
     *
     * @param login Login of Enrollee
     * @return List of EnrolleeSubject for enrollee with given login
     */
    public List<EnrolleeSubject> findAllByEnrolleeLogin(String login){
        try(EnrolleeSubjectDao dao = DaoFactory.getInstance().createEnrolleeSubjectDao()){
            return toEnrolleeSubject(dao.getAllByEnrolleeLogin(login));
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    /**
     * saves EnrolleeSubject to database
     * @param dto information to be saved
     * @throws NotUniqueSubjectException if Enrollee already filled given subject
     * @throws NoSuchSubjectException if Subject with given name is not present in database
     * @throws MarkIsNotValidException  if mark is greater than MAX_MARK or smaller than MIN_MARK
     */
    public void create(CreateEnrolleeSubjectDto dto) throws NotUniqueSubjectException, NoSuchSubjectException, MarkIsNotValidException {
        try(EnrolleeSubjectDao dao = DaoFactory.getInstance().createEnrolleeSubjectDao()){
            if (!isMarkValid(dto.getMark())){
                throw new MarkIsNotValidException();
            }
            dao.create(dto);
        }catch (SQLException ex){
            if(SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE.equals(ex.getSQLState())){
                throw new NoSuchSubjectException();
            }
            if(SQL_BREAKING_NOT_NULL_CONSTRAINT_ERROR_CODE.equals(ex.getSQLState())){
                throw new NotUniqueSubjectException();
            }
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
}
