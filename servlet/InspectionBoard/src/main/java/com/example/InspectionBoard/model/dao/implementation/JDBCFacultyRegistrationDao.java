package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.FacultyRegistrationDao;
import com.example.InspectionBoard.model.dto.parse.FacultyRegistrationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//fixme
public class JDBCFacultyRegistrationDao implements FacultyRegistrationDao {
    private static final String REGISTER = "insert into registration(enrolle_id, faculty_id) values (?, ?)";
    private final Connection connection;

    public JDBCFacultyRegistrationDao (Connection connection) {
        this.connection = connection;
    }


    @Override
    public void register(FacultyRegistrationDto dto) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(REGISTER)){
            statement.setInt(1, dto.getEnrolleeId());
            statement.setInt(2, dto.getFacultyId());
            statement.executeUpdate();
        }
    }

    @Override
    public int create(FacultyRegistrationDto facultyRegistrationDto) {
        return 0;
    }

    @Override
    public int update(FacultyRegistrationDto facultyRegistrationDto) {
        return 0;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Optional<FacultyRegistrationDto> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<FacultyRegistrationDto> findAll() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
