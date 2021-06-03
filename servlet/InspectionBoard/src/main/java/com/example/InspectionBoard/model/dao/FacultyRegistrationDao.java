package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.ChangeFacultyRegistrationStatusDto;
import com.example.InspectionBoard.model.dto.SaveFacultyRegistrationDto;
import com.example.InspectionBoard.model.dto.db.DbFacultyRegistration;

import java.sql.SQLException;

public interface FacultyRegistrationDao extends GenericDao<DbFacultyRegistration>{
    void save(SaveFacultyRegistrationDto dto) throws SQLException;
    void changeStatus(ChangeFacultyRegistrationStatusDto dto) throws SQLException;
}
