package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.AccountIsBlockedException;
import com.example.InspectionBoard.exceptions.InsertException;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import com.example.InspectionBoard.model.DTO.SaveEnrolleeDto;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.entity.Account;

public class AccountService {
    public static Account getAccount(String login, String password)
            throws WrongLoginPasswordException, AccountIsBlockedException{
        if (! isValid(login) && isValid(password)){
            throw new WrongLoginPasswordException();
        }
        String decodedLogin = ServiceUtility.decode(login);
        String decodedPassword = ServiceUtility.decode(password);
        String hashedPassword = ServiceUtility.hash(decodedPassword);
        return DaoFactory.getInstance().createAccountDao()
                .getAccount(decodedLogin, hashedPassword)
                .orElseThrow(WrongLoginPasswordException::new);
    }
    public static void createEnrollee(SaveEnrolleeDto s) throws InsertException{
        DaoFactory.getInstance().createAccountDao().createEnrollee(s);
    }
    public static void blockEnrollee(String login){
        DaoFactory.getInstance().createAccountDao().blockEnrollee(login);
    }
    public static void unblockEnrollee(String login){
        DaoFactory.getInstance().createAccountDao().unblockEnrollee(login);
    }
    private static boolean isValid(String toCheck){
        return !(toCheck == null || toCheck.trim().isEmpty());
    }
}
