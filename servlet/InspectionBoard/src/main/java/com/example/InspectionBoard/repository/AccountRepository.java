package com.example.InspectionBoard.repository;

import com.example.InspectionBoard.entity.Account;
import com.example.InspectionBoard.entity.AccountRole;
import exceptions.SQLExceptionWrapper;
import exceptions.WrongLoginPasswordException;
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
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ACCOUNT)){
            statement.setString(1, login);
            statement.setString(2, password);
            try(ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return parseAccount(rs);
                }
                throw new WrongLoginPasswordException("wrong login/password");
            }
        }catch (SQLException ex){
            LOGGER.error("parsing account", ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    private Account parseAccount(ResultSet rs) throws SQLException{
        int id = rs.getInt(1);
        AccountRole role = AccountRole.valueOf(rs.getString(2));
        return new Account(id, role);
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
