package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.db.DbFacultyRegistrationDto;

import java.sql.SQLException;

public interface FacultyRegistrationDao extends GenericDao<DbFacultyRegistrationDto>{
    void save(DbFacultyRegistrationDto dto) throws SQLException;
}
