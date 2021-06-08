package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.MarkIsNotValidException;
import com.example.InspectionBoard.exceptions.NoSuchSubjectException;
import com.example.InspectionBoard.exceptions.NotUniqueSubjectException;
import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.RequiredSubjectDao;
import com.example.InspectionBoard.model.dto.CreateFacultySubjectDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

import static com.example.InspectionBoard.Constants.SQL_BREAKING_NOT_NULL_CONSTRAINT_ERROR_CODE;
import static com.example.InspectionBoard.Constants.SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE;
import static com.example.InspectionBoard.model.service.ServiceUtility.isMarkValid;

public class RequiredSubjectService {
    private static final Logger LOGGER = LogManager.getLogger(EnrolleeSubjectService.class.getName());

    /**
     *
     * @param dto data to be inserted
     * @throws NotUniqueSubjectException if faculty already has subject with given name
     * @throws NoSuchSubjectException if subject with given name doesn't exist
     * @throws MarkIsNotValidException if mark is invalid
     */
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
}
