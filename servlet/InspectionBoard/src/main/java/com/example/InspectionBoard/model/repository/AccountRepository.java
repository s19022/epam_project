package com.example.InspectionBoard.model.repository;

import com.example.InspectionBoard.model.entity.Account;
import com.example.InspectionBoard.model.enums.AccountRole;
import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {
    private static final Logger LOGGER = LogManager.getLogger(AccountRepository.class.getName());

    private static final String FIND_ACCOUNT =  "SELECT a.id, r.name " +
                                                "FROM account a, role r " +
                                                "WHERE login = ? AND password = ? AND a.role_id = r.id";

    private static final Object LOCK = new Object();
    private static AccountRepository instance;

    private final DataSource dataSource;

    private AccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Account getAccount(String login, String password) throws WrongLoginPasswordException {
        if (! isValid(login) && isValid(password)){
            throw new WrongLoginPasswordException("wrong login/password");
        }
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ACCOUNT)){
            statement.setString(1, login);
            statement.setString(2, password);
            try(ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return parseAccount(rs);
                }
            }
        }catch (SQLException ex){
            LOGGER.error("parsing account", ex);
            throw new SQLExceptionWrapper(ex);
        }
        throw new WrongLoginPasswordException("wrong login/password");
    }

    private Account parseAccount(ResultSet rs) throws SQLException{
        int id = rs.getInt(1);
        AccountRole role = AccountRole.valueOf(rs.getString(2));
        return new Account(id, role);
    }

    private boolean isValid(String toCheck){
        return !(toCheck == null || toCheck.trim().isEmpty());
    }

    public static AccountRepository getInstance() {
        if (instance == null){
            synchronized (LOCK){
                if (instance == null){
                    instance = new AccountRepository(DataSourceWrapper.getDataSource());
                }
            }
        }
        return instance;
    }
}
