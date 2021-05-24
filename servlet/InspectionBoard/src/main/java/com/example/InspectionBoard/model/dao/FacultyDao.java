package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.entity.Faculty;

public interface FacultyDao extends GenericDao<Faculty>{
    Faculty getByName(String name);
}
