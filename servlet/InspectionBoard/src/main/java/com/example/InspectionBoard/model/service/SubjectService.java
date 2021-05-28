package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.SubjectDao;
import com.example.InspectionBoard.model.dto.db.DbSubjectDto;
import com.example.InspectionBoard.model.entity.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class SubjectService {
    private static final Logger LOGGER = LogManager.getLogger(SubjectService.class.getName());

    public static List<Subject> findAll(){
        try(SubjectDao dao = DaoFactory.getInstance().createSubjectDao()){
            return toSubject(dao.findAll());
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    private static List<Subject> toSubject(List<DbSubjectDto> dto){
        return dto.stream().map(SubjectService::toSubject).collect(Collectors.toList());
    }

    private static Subject toSubject(DbSubjectDto dto){
        return new Subject(dto.getId(), dto.getName());
    }
}