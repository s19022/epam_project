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

    /**
     *
     * @param login login of account
     * @param password password of account
     * @return Account identified by login and password
     * @throws WrongLoginPasswordException if there are no account with given login and password
     * @throws AccountIsBlockedException if found account is blocked
     */
    public Account getAccount(String login, String password)
            throws WrongLoginPasswordException, AccountIsBlockedException{
        String hashedPassword = ServiceUtility.hash(password);

        try (AccountDao dao = DaoFactory.getInstance().createAccountDao()){
            DbAccountDto accountDto =
                    dao.findByLoginAndPassword(login, hashedPassword)
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

    /**
     * saves Enrollee to database
     * @param s details of Enrollee to be saved
     */
    public void createEnrollee(SaveEnrolleeDto s){
        try (AccountDao dao = DaoFactory.getInstance().createAccountDao()){
            dao.getConnection().setAutoCommit(false);
            try{
                int id = dao.insertAccount(s);
                dao.insertEnrollee(s, id);
            }catch (SQLException e) {
                dao.getConnection().rollback();
                throw e;
            }finally {
                dao.getConnection().commit();
                dao.getConnection().setAutoCommit(true);
            }
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }

    }

    /**
     * Sets isBlocked of enrollee with given login to true
     * @param login enrollee login
     *
     */
    public void blockEnrollee(String login){
        try (AccountDao dao = DaoFactory.getInstance().createAccountDao()){
            dao.blockEnrollee(login);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    /**
     * Sets isBlocked of enrollee with given login to false
     * @param login enrollee login
     *
     */
    public void unblockEnrollee(String login){
        try (AccountDao dao = DaoFactory.getInstance().createAccountDao()){
            dao.unblockEnrollee(login);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
}
