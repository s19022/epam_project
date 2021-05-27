package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.parse.FacultyRegistrationDto;

import java.sql.SQLException;

public interface FacultyRegistrationDao extends GenericDao<FacultyRegistrationDto>{
    void register(FacultyRegistrationDto dto) throws SQLException;
}
