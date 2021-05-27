package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.*;
import com.example.InspectionBoard.model.dto.SaveEnrolleeDto;
import com.example.InspectionBoard.model.dto.parse.ParseAccountDto;
import com.example.InspectionBoard.model.enums.AccountRole;
import com.example.InspectionBoard.model.service.ServiceUtility;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JDBCAccountDao implements AccountDao {
    private static final int USER_ROLE_ID = 1;
    private static final String FIND_BY_LOGIN_AND_PASSWORD =  "SELECT a.blocked, a.id, r.name, a.login " +
                                                "FROM account a, role r " +
                                                "WHERE login = ? AND password = ? AND a.role_id = r.id";
    private static final String FIND_BY_LOGIN =  "SELECT a.blocked, a.id, r.name, a.login " +
            "FROM account a, role r " +
            "WHERE login = ? AND a.role_id = r.id";

    private static final String INSERT_ACCOUNT ="insert into account(login, password, role_id)" +
           " values (?, ?," + USER_ROLE_ID + " )";
    private static final String INSERT_ENROLLEE = "insert into enrollee(id, first_name, father_name," +
                                                    " last_name, email," +
                                                    " city, region, school_name) " +
                                                  "values(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String BLOCK_ENROLLEE = "UPDATE account SET blocked = TRUE WHERE login = ?" +
            " and role_id = "  + USER_ROLE_ID;
    private static final String UNBLOCK_ENROLLEE = "UPDATE account SET blocked = FALSE WHERE login = ?" +
            " and role_id = "  + USER_ROLE_ID;


    private final Connection connection;

    public JDBCAccountDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<ParseAccountDto> findByLoginAndPassword(String login, String password) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement(FIND_BY_LOGIN_AND_PASSWORD)){
            statement.setString(1, login);
            statement.setString(2, password);
            try(ResultSet rs = statement.executeQuery()) {
                if (rs.next()){
                    return Optional.of(parseAccount(rs));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public void blockEnrollee(String login) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(BLOCK_ENROLLEE)){
            statement.setString(1, login);
            statement.executeUpdate();
        }
    }

    @Override
    public void unblockEnrollee(String login) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UNBLOCK_ENROLLEE)){
            statement.setString(1, login);
            statement.executeUpdate();
        }
    }

    @Override
    public void insertEnrollee(SaveEnrolleeDto enrollee, int id) throws SQLException {
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

    @Override
    public int insertAccount(SaveEnrolleeDto enrollee) throws SQLException{
        try(PreparedStatement statement =
                    connection.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)){
            String passwordHashed = ServiceUtility.hash(enrollee.getPassword());
            statement.setString(1, enrollee.getLogin());
            statement.setString(2, passwordHashed);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    @Override
    public Optional<ParseAccountDto> findByLogin(String login) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_BY_LOGIN)){
            statement.setString(1, login);
            try(ResultSet rs = statement.executeQuery()) {
                if (rs.next()){
                    return Optional.of(parseAccount(rs));
                }
                return Optional.empty();
            }
        }

    }

    @Override
    public List<ParseAccountDto> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
    private ParseAccountDto parseAccount(ResultSet rs) throws SQLException{
        boolean blocked = rs.getBoolean(1);
        int id = rs.getInt(2);
        AccountRole role = AccountRole.valueOf(rs.getString(3));
        String login = rs.getString(4);
        return new ParseAccountDto(id, role, blocked, login);
    }
}
