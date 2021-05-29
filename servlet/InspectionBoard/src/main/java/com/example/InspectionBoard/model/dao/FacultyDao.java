package com.example.InspectionBoard.model.dao;

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
    List<DbFacultyDto> findByEnrolleeLoginEquals(String login) throws SQLException;
}
