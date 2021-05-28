package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.db.DbFacultyRegistrationDto;

import java.sql.SQLException;

public interface FacultyRegistrationDao extends GenericDao<DbFacultyRegistrationDto>{
    void register(DbFacultyRegistrationDto dto) throws SQLException;
}
