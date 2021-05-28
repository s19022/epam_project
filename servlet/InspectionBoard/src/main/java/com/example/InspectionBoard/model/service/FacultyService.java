package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.NoSuchFacultyException;
import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.FacultyDao;
import com.example.InspectionBoard.model.entity.Faculty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

import static com.example.InspectionBoard.model.dto.db.Mapper.toFaculty;
import static com.example.InspectionBoard.model.service.ServiceUtility.isValid;

public class FacultyService {
    private static final Logger LOGGER = LogManager.getLogger(FacultyService.class.getName());

    public static List<Faculty> findAll(){
        try(FacultyDao dao = DaoFactory.getInstance().createFacultyDao()){
            return toFaculty(dao.findAll());
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    public static void deleteByFacultyName(String facultyName){
        if (!isValid(facultyName)){
            LOGGER.warn("Empty faculty name");
            return;
        }
        try(FacultyDao dao = DaoFactory.getInstance().createFacultyDao()){
            dao.deleteByFacultyName(facultyName);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    public static Faculty getByName(String name) throws NoSuchFacultyException {
        if (!isValid(name)){
            throw new NoSuchFacultyException();
        }
        try(FacultyDao dao = DaoFactory.getInstance().createFacultyDao()){
            return toFaculty(dao.getByName(name).orElseThrow(NoSuchFacultyException::new));
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
}
