package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.db.DbEnrolleeDto;
import com.example.InspectionBoard.model.dto.db.FindByPageDto;

import java.sql.SQLException;
import java.util.List;

public interface EnrolleeDao extends GenericDao<DbEnrolleeDto>{
    List<DbEnrolleeDto> findAllLimitAndOffset(int limit, int offset) throws SQLException;
    int getNumberOfEnrollees() throws SQLException;
}
