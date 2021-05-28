package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.db.DbParseEnrolleeSubjectDto;

import java.sql.SQLException;
import java.util.List;

public interface EnrolleeSubjectDao extends GenericDao<DbParseEnrolleeSubjectDto>{
    List<DbParseEnrolleeSubjectDto> getAllByEnrolleeId(int id) throws SQLException;
}
