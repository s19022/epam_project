package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dto.SaveEnrolleeDto;
import com.example.InspectionBoard.model.dto.db.DbAccountDto;

import java.sql.SQLException;
import java.util.Optional;

public interface AccountDao extends GenericDao<DbAccountDto> {
    Optional<DbAccountDto> findByLoginAndPassword(String login, String password) throws  SQLException;
    void blockEnrollee(String login) throws SQLException;
    void unblockEnrollee(String login) throws SQLException;
    void insertEnrollee(SaveEnrolleeDto enrollee, int id) throws SQLException;
    int insertAccount(SaveEnrolleeDto enrollee) throws SQLException;
    Optional<DbAccountDto> findByLogin(String login) throws SQLException;
}
