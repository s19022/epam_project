package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.exceptions.AccountIsBlockedException;
import com.example.InspectionBoard.exceptions.InsertException;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import com.example.InspectionBoard.model.DTO.SaveEnrolleeDto;
import com.example.InspectionBoard.model.entity.Account;

import java.util.Optional;

public interface AccountDao extends GenericDao<Account> {
    Optional<Account> getAccount(String login, String password)
            throws WrongLoginPasswordException, AccountIsBlockedException;
    void createEnrollee(SaveEnrolleeDto s) throws InsertException;
    void blockEnrollee(String login);
    void unblockEnrollee(String login);

}
