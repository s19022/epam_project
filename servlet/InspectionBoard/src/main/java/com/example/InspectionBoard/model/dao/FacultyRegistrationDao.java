package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.SaveFacultyRegistrationDto;
import com.example.InspectionBoard.model.dto.db.DbFacultyRegistrationStatus;

import java.sql.SQLException;

public interface FacultyRegistrationDao extends GenericDao<DbFacultyRegistrationStatus>{
    void save(SaveFacultyRegistrationDto dto) throws SQLException;
}
