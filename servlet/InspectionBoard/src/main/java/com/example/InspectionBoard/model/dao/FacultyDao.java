package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.CreateFacultyDto;
import com.example.InspectionBoard.model.dto.ModifyFacultyDto;
import com.example.InspectionBoard.model.dto.db.DbFacultyDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FacultyDao extends GenericDao<DbFacultyDto>{
    Optional<DbFacultyDto> findByName(String name) throws SQLException;
    void deleteByFacultyName(String facultyName) throws SQLException;
    List<DbFacultyDto> findAllOrderByNameAsc() throws SQLException;
    List<DbFacultyDto> findAllOrderByNameDesc() throws SQLException;
    List<DbFacultyDto> findAllOrderByBudgetPlacesDesc() throws SQLException;
    List<DbFacultyDto> findAllOrderByAllPlacesDesc() throws SQLException;
    void subtractBudgetPlace(String facultyName) throws SQLException;
    void subtractContractPlace(String facultyName) throws SQLException;
    void create(CreateFacultyDto dto) throws SQLException;
    void update(ModifyFacultyDto dto) throws SQLException;
}

