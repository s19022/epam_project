package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.entity.RequiredSubject;

import java.sql.SQLException;
import java.util.List;

public interface RequiredSubjectDao extends GenericDao<RequiredSubject> {
    List<RequiredSubject> getAllByFacultyId(int facultyId) throws SQLException;
}
