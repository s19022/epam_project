package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.DTO.SaveEnrollee;
import com.example.InspectionBoard.model.entity.Account;

public interface AccountDao extends GenericDao<Account> {
    Account getAccount(String login, String password);
    int createEnrollee(SaveEnrollee s);
    void blockEnrollee(String login);
    void unblockEnrollee(String login);

}
