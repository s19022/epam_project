package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.FacultyRegistrationDao;
import com.example.InspectionBoard.model.dto.SaveFacultyRegistrationDto;
import com.example.InspectionBoard.model.dto.db.DbFacultyRegistration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCFacultyRegistrationDao implements FacultyRegistrationDao {
    private static final int PENDING_ID = 1;
    private static final String REGISTER =
            "insert into registration(enrollee_id, faculty_id, registration_status_id)" +
            " values (?, ?," + PENDING_ID + ")";
    private static final String FIND_ALL =
            "SELECT r.id, s.status_name, a.id, a.login, f.id, f.name " +
            "FROM registration r, faculty f, account a, registration_status s " +
            "WHERE r.enrollee_id = a.id AND r.faculty_id = f.id AND r.registration_status_id = s.id";

    private final Connection connection;

    public JDBCFacultyRegistrationDao (Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(SaveFacultyRegistrationDto dto) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(REGISTER)){
            statement.setInt(1, dto.getEnrolleeId());
            statement.setInt(2, dto.getFacultyId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<DbFacultyRegistration> findAll() throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            ResultSet rs = statement.executeQuery()){
            return parseList(rs);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    private static List<DbFacultyRegistration> parseList(ResultSet rs) throws SQLException {
        List<DbFacultyRegistration> out = new ArrayList<>();
        while (rs.next()){
            out.add(parse(rs));
        }
        return out;
    }

    private static DbFacultyRegistration parse(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String status = rs.getString(2);
        int enrolleeId = rs.getInt(3);
        String enrolleeLogin = rs.getString(4);
        int facultyId = rs.getInt(5);
        String facultyName = rs.getString(6);
        return new DbFacultyRegistration(id, status, enrolleeId, enrolleeLogin, facultyId, facultyName);
    }
}
