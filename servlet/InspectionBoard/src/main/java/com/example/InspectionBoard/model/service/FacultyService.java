package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.NoSuchFacultyException;
import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.FacultyDao;
import com.example.InspectionBoard.model.dto.db.DbFacultyDto;
import com.example.InspectionBoard.model.dto.db.DbRequiredSubjectDto;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.entity.RequiredSubject;
import com.example.InspectionBoard.model.entity.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

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

    private static List<Faculty> toFaculty(List<DbFacultyDto> dto){
        return dto.stream().map(FacultyService::toFaculty).collect(Collectors.toList());
    }

    private static Faculty toFaculty(DbFacultyDto dto){
        int id = dto.getId();
        String name = dto.getName();
        int budgetPlaces = dto.getBudgetPlaces();
        int allPlaces = dto.getAllPlaces();
        List<DbRequiredSubjectDto> requiredSubjectsDto = dto.getRequiredSubject();
        List<RequiredSubject> requiredSubjects = requiredSubjectsDto.stream()
                .map(FacultyService::toRequiredSubject).collect(Collectors.toList());
        return new Faculty(id, name, budgetPlaces, allPlaces, requiredSubjects);
    }

    private static RequiredSubject toRequiredSubject(DbRequiredSubjectDto dto){
        return new RequiredSubject(new Subject(dto.getId(), dto.getName()), dto.getMinimalGrade());
    }
}
