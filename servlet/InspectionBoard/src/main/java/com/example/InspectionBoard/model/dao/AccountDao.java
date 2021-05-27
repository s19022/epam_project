package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.exceptions.AccountIsBlockedException;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import com.example.InspectionBoard.model.dto.SaveEnrolleeDto;
import com.example.InspectionBoard.model.entity.Account;

import java.sql.SQLException;
import java.util.Optional;

public interface AccountDao extends GenericDao<Account> {
    Optional<Account> getAccount(String login, String password)
            throws WrongLoginPasswordException, AccountIsBlockedException, SQLException;
    void blockEnrollee(String login) throws SQLException;
    void unblockEnrollee(String login) throws SQLException;
    void insertEnrollee(SaveEnrolleeDto enrollee, int id) throws SQLException;
    int insertAccount(SaveEnrolleeDto enrollee) throws SQLException;
}
