package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.EnrolleeSubjectDao;
import com.example.InspectionBoard.model.entity.EnrolleeSubject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

import static com.example.InspectionBoard.model.dto.db.Mapper.toEnrolleeSubject;

public class EnrolleeSubjectService {
    private static final Logger LOGGER = LogManager.getLogger(EnrolleeSubjectService.class.getName());

    public static List<EnrolleeSubject> findAllByEnrolleeLogin(String login){
        try(EnrolleeSubjectDao dao = DaoFactory.getInstance().createEnrolleeSubjectDao()){
            return toEnrolleeSubject(dao.getAllByEnrolleeLogin(login));
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
}
