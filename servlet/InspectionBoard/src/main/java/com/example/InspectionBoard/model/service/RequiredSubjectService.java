package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.RequiredSubjectDao;
import com.example.InspectionBoard.model.dto.db.DbRequiredSubjectDto;
import com.example.InspectionBoard.model.entity.RequiredSubject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class RequiredSubjectService {
    private static final Logger LOGGER = LogManager.getLogger(RequiredSubject.class.getName());

    public static List<DbRequiredSubjectDto> getAllByFacultyId(int facultyId) {
        try(RequiredSubjectDao dao = DaoFactory.getInstance().createRequiredSubjectDao()){
            return dao.getAllByFacultyId(facultyId);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
}