package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.EnrolleeSubjectDao;
import com.example.InspectionBoard.model.dto.CreateEnrolleeSubjectDto;
import com.example.InspectionBoard.model.dto.db.DbEnrolleeSubjectDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCEnrolleeSubjectDao implements EnrolleeSubjectDao {
    private static final String FIND_ALL_BY_ENROLLEE_ID =
            "SELECT m.subject_id, s.name, m.mark " +
            "FROM enrollee_subject m, subject s " +
            "WHERE m.enrollee_id = ? and s.id = m.subject_id " +
            "FOR UPDATE ";

    private static final String FIND_ALL_BY_ENROLLEE_LOGIN =
            "SELECT m.subject_id, s.name, m.mark " +
            "FROM enrollee_subject m, subject s, account a " +
            "WHERE a.login = ? and a.id = m.enrollee_id and s.id = m.subject_id " +
            "FOR UPDATE ";

    private static final String CREATE_SUBJECT ="INSERT INTO enrollee_subject(enrollee_id, subject_id, mark) values" +
            " ((SELECT a.id from account a where a.login = ?), (SELECT s.id from subject s where s.name = ?), ?)";

    private final Connection connection;

    public JDBCEnrolleeSubjectDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public List<DbEnrolleeSubjectDto> getAllByEnrolleeId(int id) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_ENROLLEE_ID)){
            statement.setInt(1, id);
            return parseSubjects(statement.executeQuery());
        }
    }

    @Override
    public List<DbEnrolleeSubjectDto> getAllByEnrolleeLogin(String login) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_ENROLLEE_LOGIN)){
            statement.setString(1, login);
            return parseSubjects(statement.executeQuery());
        }
    }

    @Override
    public void create(CreateEnrolleeSubjectDto dto) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(CREATE_SUBJECT)){
            statement.setString(1, dto.getEnrolleeName());
            statement.setString(2, dto.getSubjectName());
            statement.setInt(3, dto.getMark());
            statement.executeUpdate();
        }
    }

    private static List<DbEnrolleeSubjectDto> parseSubjects(ResultSet rs) throws SQLException {
        List<DbEnrolleeSubjectDto> out = new ArrayList<>();
        while (rs.next()){
            out.add(parseSubject(rs));
        }
        return out;
    }

    private static DbEnrolleeSubjectDto parseSubject(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        int mark = rs.getInt(3);
        return new DbEnrolleeSubjectDto(id, name, mark);
    }
}
