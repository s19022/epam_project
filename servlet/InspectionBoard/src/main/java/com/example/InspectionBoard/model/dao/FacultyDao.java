package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.entity.Faculty;

import java.util.Optional;

public interface FacultyDao extends GenericDao<Faculty>{
    Optional<Faculty> getByName(String name);
}
