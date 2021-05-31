package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.EnrolleeDao;
import com.example.InspectionBoard.model.dto.db.DbEnrolleeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCEnrolleeDao implements EnrolleeDao {
    private static final String FIND_ALL =
            "SELECT id, first_name, last_name, father_name, email, city, region, school_name " +
            "FROM enrollee ";

    private static final String FIND_ALL_LIMIT_AND_OFFSET = FIND_ALL + " LIMIT ? OFFSET ? ";
    private final Connection connection;

    public JDBCEnrolleeDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public List<DbEnrolleeDto> findAll() throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL)){
            return parseEnrolleeList(statement.executeQuery());
        }
    }

    @Override
    public List<DbEnrolleeDto> findAllLimitAndOffset(int limit, int offset) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_LIMIT_AND_OFFSET)){
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            return parseEnrolleeList(statement.executeQuery());
        }
    }

    private static List<DbEnrolleeDto> parseEnrolleeList(ResultSet rs) throws SQLException {
        List<DbEnrolleeDto> list = new ArrayList<>();
        while (rs.next()){
            list.add(parseEnrollee(rs));
        }
        return list;
    }

    private static DbEnrolleeDto parseEnrollee(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String firstName = rs.getString(2);
        String lastName = rs.getString(3);
        String fatherName = rs.getString(4);
        String email = rs.getString(5);
        String city = rs.getString(6);
        String region = rs.getString(7);
        String schoolName = rs.getString(8);
        return new DbEnrolleeDto(id, firstName, lastName, fatherName, email, city, region, schoolName);
    }
}
