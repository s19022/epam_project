package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.CreateEnrolleeSubjectDto;
import com.example.InspectionBoard.model.dto.db.DbEnrolleeSubjectDto;

import java.sql.SQLException;
import java.util.List;

public interface EnrolleeSubjectDao extends GenericDao<DbEnrolleeSubjectDto>{
    List<DbEnrolleeSubjectDto> getAllByEnrolleeId(int id) throws SQLException;
    List<DbEnrolleeSubjectDto> getAllByEnrolleeLogin(String login) throws SQLException;
    List<DbEnrolleeSubjectDto> findNotTakenByEnrolleeLogin(String login) throws SQLException;
    void create(CreateEnrolleeSubjectDto dto) throws SQLException;
}
