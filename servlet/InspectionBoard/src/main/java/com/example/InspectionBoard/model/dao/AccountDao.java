package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.exceptions.AccountIsBlockedException;
import com.example.InspectionBoard.exceptions.InsertException;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import com.example.InspectionBoard.model.DTO.SaveEnrollee;
import com.example.InspectionBoard.model.entity.Account;

public interface AccountDao extends GenericDao<Account> {
    Account getAccount(String login, String password)
            throws WrongLoginPasswordException, AccountIsBlockedException;
    int createEnrollee(SaveEnrollee s) throws InsertException;
    void blockEnrollee(String login);
    void unblockEnrollee(String login);

}
