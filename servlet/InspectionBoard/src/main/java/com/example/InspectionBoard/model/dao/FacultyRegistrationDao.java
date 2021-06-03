package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.SaveFacultyRegistrationDto;

import java.sql.SQLException;

public interface FacultyRegistrationDao extends GenericDao<SaveFacultyRegistrationDto>{
    void save(SaveFacultyRegistrationDto dto) throws SQLException;
}
