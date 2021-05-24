package com.example.InspectionBoard.model.repository;

import com.example.InspectionBoard.exceptions.AccountIsBlockedException;
import com.example.InspectionBoard.exceptions.InsertException;
import com.example.InspectionBoard.model.DTO.SaveEnrollee;
import com.example.InspectionBoard.model.entity.Account;
import com.example.InspectionBoard.model.enums.AccountRole;
import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;

public class AccountRepository {
    private static final Logger LOGGER = LogManager.getLogger(AccountRepository.class.getName());
    private static final int USER_ROLE_ID = 1;
    private static final String FIND_ACCOUNT =  "SELECT a.blocked, a.id, r.name " +
                                                "FROM account a, role r " +
                                                "WHERE login = ? AND password = ? AND a.role_id = r.id";
   private static final String INSERT_ACCOUNT =
           "insert into account(login, password, role_id) values (?, ?," + USER_ROLE_ID + " )";
    private static final String INSERT_ENROLLEE = "insert into enrollee(id, first_name, father_name," +
                                                    " last_name, email," +
                                                    " city, region, school_name) " +
                                                  "values(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String BLOCK_ENROLLEE = "UPDATE account SET blocked = TRUE WHERE login = ?" +
            " and role_id = "  + USER_ROLE_ID;
    private static final String UNBLOCK_ENROLLEE = "UPDATE account SET blocked = FALSE WHERE login = ?" +
            " and role_id = "  + USER_ROLE_ID;


    private static final Object LOCK = new Object();
    private static AccountRepository instance;

    private final DataSource dataSource;

    private AccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Account getAccount(String login, String password)
            throws WrongLoginPasswordException, AccountIsBlockedException {
        if (! isValid(login) && isValid(password)){
            throw new WrongLoginPasswordException("wrong login/password");
        }
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ACCOUNT)){
            statement.setString(1, login);
            statement.setString(2, HashUtility.encodePassword(password));
            try(ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return parseAccount(rs);
                }
            }
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
        throw new WrongLoginPasswordException("wrong login/password");
    }

    public void saveUser(SaveEnrollee enrollee) throws InsertException {
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            try {
                int accountId = insertAccount(connection, enrollee);
                insertEnrollee(connection, enrollee, accountId);
            } catch (Exception ex) {
                connection.rollback();
                throw new InsertException(ex);
            }
            connection.commit();
            connection.setAutoCommit(true);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    public void blockEnrollee(String login){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(BLOCK_ENROLLEE)){
            statement.setString(1, login);
            statement.executeUpdate();
            //todo check if blocked
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
            //todo one method to exception handling
        }
    }

    public void unblockEnrollee(String login){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(UNBLOCK_ENROLLEE)){
            statement.setString(1, login);
            statement.executeUpdate();
            //todo check if blocked
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
            //todo one method to exception handling
        }
    }

    private int insertAccount(Connection connection, SaveEnrollee enrollee) throws SQLException{
        try(PreparedStatement statement =
                    connection.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)){
            String passwordHashed = HashUtility.encodePassword(enrollee.getPassword());
            statement.setString(1, enrollee.getLogin());
            statement.setString(2, passwordHashed);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    private void insertEnrollee(Connection connection, SaveEnrollee enrollee, int id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(INSERT_ENROLLEE)) {
            statement.setInt(1, id);
            statement.setString(2, enrollee.getFirstName());
            statement.setString(3, enrollee.getFatherName());
            statement.setString(4, enrollee.getLastName());
            statement.setString(5, enrollee.getEmail());
            statement.setString(6, enrollee.getCity());
            statement.setString(7, enrollee.getRegion());
            statement.setString(8, enrollee.getSchoolName());
            statement.executeUpdate();
            //todo save scan
//            statement.setBinaryStream(9, new ByteArrayInputStream(enrollee.getCertificateScan()));
        }
    }

    private Account parseAccount(ResultSet rs) throws SQLException, AccountIsBlockedException{
        boolean blocked = rs.getBoolean(1);
        if (blocked){
            throw new AccountIsBlockedException();
        }
        int id = rs.getInt(2);
        AccountRole role = AccountRole.valueOf(rs.getString(3));

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
