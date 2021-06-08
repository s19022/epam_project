package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.SubjectDao;
import com.example.InspectionBoard.model.entity.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

import static com.example.InspectionBoard.model.dto.db.Mapper.toSubject;

public class SubjectService {
    private static final Logger LOGGER = LogManager.getLogger(EnrolleeSubjectService.class.getName());

    public List<Subject> findNotTakenByEnrolleeLogin(String login){
        try(SubjectDao dao = DaoFactory.getInstance().createSubjectDao()){
            return toSubject(dao.findNotTakenByEnrolleeLogin(login));
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    public List<Subject> findNotTakenByFacultyName(String facultyName){
        try(SubjectDao dao = DaoFactory.getInstance().createSubjectDao()){
            return toSubject(dao.findNotTakenByFacultyName(facultyName));
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
}
