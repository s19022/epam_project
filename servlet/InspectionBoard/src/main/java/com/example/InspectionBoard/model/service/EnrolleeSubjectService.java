package com.example.InspectionBoard.model.service;

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

import static com.example.InspectionBoard.Constants.SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE;
import static com.example.InspectionBoard.model.dto.db.Mapper.toEnrolleeSubject;

public class EnrolleeSubjectService {
    private static final Logger LOGGER = LogManager.getLogger(EnrolleeSubjectService.class.getName());

    public List<EnrolleeSubject> findAllByEnrolleeLogin(String login){
        try(EnrolleeSubjectDao dao = DaoFactory.getInstance().createEnrolleeSubjectDao()){
            return toEnrolleeSubject(dao.getAllByEnrolleeLogin(login));
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    public void create(CreateEnrolleeSubjectDto dto) throws NotUniqueSubjectException {
        try(EnrolleeSubjectDao dao = DaoFactory.getInstance().createEnrolleeSubjectDao()){
            dao.create(dto);
        }catch (SQLException ex){
            if(SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE.equals(ex.getSQLState())){
                throw new NotUniqueSubjectException();
            }
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
}
