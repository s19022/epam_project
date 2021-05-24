package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dao.GenericDao;
import com.example.InspectionBoard.model.entity.RequiredSubject;

import java.util.List;

public interface RequiredSubjectDao extends GenericDao<RequiredSubject> {
    List<RequiredSubject> getAllByFacultyId(int facultyId);
}
