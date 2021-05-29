package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.FacultyRegistrationDao;
import com.example.InspectionBoard.model.dto.db.DbFacultyRegistrationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JDBCFacultyRegistrationDao implements FacultyRegistrationDao {
    private static final String REGISTER = "insert into registration(enrollee_id, faculty_id) values (?, ?)";
    private final Connection connection;

    public JDBCFacultyRegistrationDao (Connection connection) {
        this.connection = connection;
    }


    @Override
    public void register(DbFacultyRegistrationDto dto) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(REGISTER)){
            statement.setInt(1, dto.getEnrolleeId());
            statement.setInt(2, dto.getFacultyId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<DbFacultyRegistrationDto> findAll() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
