package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.db.DbEnrolleeSubjectDto;

import java.sql.SQLException;
import java.util.List;

public interface EnrolleeSubjectDao extends GenericDao<DbEnrolleeSubjectDto>{
    List<DbEnrolleeSubjectDto> getAllByEnrolleeId(int id) throws SQLException;
    List<DbEnrolleeSubjectDto> getAllByEnrolleeLogin(String login) throws SQLException;
}
