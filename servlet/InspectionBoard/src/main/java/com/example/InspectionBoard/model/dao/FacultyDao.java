package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.db.DbFacultyDto;

import java.sql.SQLException;
import java.util.Optional;

public interface FacultyDao extends GenericDao<DbFacultyDto>{
    Optional<DbFacultyDto> getByName(String name) throws SQLException;
    void deleteByFacultyName(String facultyName) throws SQLException;
}
