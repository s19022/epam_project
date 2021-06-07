package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.db.DbEnrolleeSubjectDto;
import com.example.InspectionBoard.model.dto.db.DbSubjectDto;

import java.sql.SQLException;
import java.util.List;

public interface SubjectDao extends GenericDao<DbSubjectDto>{
    List<DbSubjectDto> findNotTakenByEnrolleeLogin(String login) throws SQLException;
}
