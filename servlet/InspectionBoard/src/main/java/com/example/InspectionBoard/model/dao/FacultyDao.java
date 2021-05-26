package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.entity.Faculty;

import java.sql.SQLException;
import java.util.Optional;

public interface FacultyDao extends GenericDao<Faculty>{
    Optional<Faculty> getByName(String name) throws SQLException;
}
