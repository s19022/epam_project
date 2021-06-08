package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.MarkIsNotValidException;
import com.example.InspectionBoard.exceptions.NoSuchSubjectException;
import com.example.InspectionBoard.exceptions.NotUniqueSubjectException;
import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.EnrolleeSubjectDao;
import com.example.InspectionBoard.model.dao.RequiredSubjectDao;
import com.example.InspectionBoard.model.dto.CreateEnrolleeSubjectDto;
import com.example.InspectionBoard.model.dto.CreateFacultySubjectDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

import static com.example.InspectionBoard.Constants.SQL_BREAKING_NOT_NULL_CONSTRAINT_ERROR_CODE;
import static com.example.InspectionBoard.Constants.SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE;

public class RequiredSubjectService {
    private static final Logger LOGGER = LogManager.getLogger(EnrolleeSubjectService.class.getName());
    private static final int MAX_MARK = 12;
    private static final int MIN_MARK = 1;

    public void create(CreateFacultySubjectDto dto) throws NotUniqueSubjectException, NoSuchSubjectException, MarkIsNotValidException {
        try(RequiredSubjectDao dao = DaoFactory.getInstance().createRequiredSubjectDao()){
            if (!isMarkValid(dto.getMinimalMark())){
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

    private boolean isMarkValid(int mark){
        return mark >= MIN_MARK && mark <= MAX_MARK;
    }
}
