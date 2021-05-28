package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.db.DbFacultyDto;
import com.example.InspectionBoard.model.entity.Faculty;

import java.sql.SQLException;
import java.util.Optional;

public interface FacultyDao extends GenericDao<DbFacultyDto>{
    Optional<DbFacultyDto> getByName(String name) throws SQLException;
}
