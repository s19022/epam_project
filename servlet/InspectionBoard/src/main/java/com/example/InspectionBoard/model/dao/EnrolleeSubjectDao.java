package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.parse.ParseEnrolleeSubjectDto;

import java.sql.SQLException;
import java.util.List;

public interface EnrolleeSubjectDao extends GenericDao<ParseEnrolleeSubjectDto>{
    List<ParseEnrolleeSubjectDto> getAllByEnrolleeId(int id) throws SQLException;
}
