package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.CreateFacultySubjectDto;
import com.example.InspectionBoard.model.dto.db.DbRequiredSubjectDto;

import java.sql.SQLException;
import java.util.List;

public interface RequiredSubjectDao extends GenericDao<DbRequiredSubjectDto> {
    List<DbRequiredSubjectDto> getAllByFacultyId(int facultyId) throws SQLException;
    void create(CreateFacultySubjectDto dto) throws SQLException;
}
