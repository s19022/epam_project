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
            "FROM mark m, subject s " +
            "WHERE m.enrollee_id = ? and s.id = m.subject_id " +
            "FOR UPDATE ";

    private static final String FIND_ALL_BY_ENROLLEE_LOGIN =
            "SELECT m.subject_id, s.name, m.mark " +
            "FROM mark m, subject s, account a " +
            "WHERE a.login = ? and a.id = m.enrollee_id and s.id = m.subject_id " +
            "FOR UPDATE ";

    private final Connection connection;

    public JDBCEnrolleeSubjectDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public List<DbEnrolleeSubjectDto> findAll(){
        return new ArrayList<>();
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
