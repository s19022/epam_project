package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.AccountIsBlockedException;
import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import com.example.InspectionBoard.model.dto.SaveEnrolleeDto;
import com.example.InspectionBoard.model.dao.AccountDao;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dto.db.DbAccountDto;
import com.example.InspectionBoard.model.entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

import static com.example.InspectionBoard.model.dto.db.Mapper.toAccount;

public class AccountService {
    private static final Logger LOGGER = LogManager.getLogger(AccountService.class.getName());

    public static Account getAccount(String login, String password)
            throws WrongLoginPasswordException, AccountIsBlockedException{
        String decodedLogin = ServiceUtility.decode(login);
        String decodedPassword = ServiceUtility.decode(password);
        String hashedPassword = ServiceUtility.hash(decodedPassword);

        try (AccountDao dao = DaoFactory.getInstance().createAccountDao()){
            DbAccountDto accountDto =
                    dao.findByLoginAndPassword(decodedLogin, hashedPassword)
                            .orElseThrow(WrongLoginPasswordException::new);
            if (accountDto.isBlocked()){
                throw new AccountIsBlockedException();
            }
            return toAccount(accountDto);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    public static void createEnrollee(SaveEnrolleeDto s){
        try (AccountDao dao = DaoFactory.getInstance().createAccountDao()){
            dao.getConnection().setAutoCommit(false);
            try{
                int id = dao.insertAccount(s);
                dao.insertEnrollee(s, id);
                dao.getConnection().commit();
            }catch (SQLException e) {
                dao.getConnection().rollback();
            }
            dao.getConnection().setAutoCommit(true);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }

    }

    public static void blockEnrollee(String login){
        try (AccountDao dao = DaoFactory.getInstance().createAccountDao()){
            dao.blockEnrollee(login);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
    public static void unblockEnrollee(String login){
        try (AccountDao dao = DaoFactory.getInstance().createAccountDao()){
            dao.unblockEnrollee(login);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
}
