package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.SubjectDao;
import com.example.InspectionBoard.model.dto.db.DbSubjectDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSubjectDao implements SubjectDao {
    private static final String GET_ALL_SUBJECTS =  "SELECT id, name FROM subject";
    private static final String FIND_NOT_TAKEN_BY_ENROLLEE_LOGIN =
            "select s.id, s.name " +
            "from subject s " +
            "where s.id not in ( " +
                "select m.subject_id " +
                "from enrollee_subject m, account a " +
                "where m.enrollee_id = a.id AND a.login = ?)";

    private static final String FIND_NOT_TAKEN_BY_FACULTY_NAME =
            "select s.id, s.name " +
            "from subject s " +
            "where s.id not in ( " +
                "select r.subject_id " +
                "from faculty f, required_subject r " +
                "where f.id = r.faculty_id AND f.name = ?)";


    private final Connection connection;

    public JDBCSubjectDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<DbSubjectDto> findAll() throws SQLException {
        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_SUBJECTS)){
            return parseSubjects(rs);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public List<DbSubjectDto> findNotTakenByEnrolleeLogin(String login) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_NOT_TAKEN_BY_ENROLLEE_LOGIN)){
            statement.setString(1, login);
            return parseSubjects(statement.executeQuery());
        }
    }

    @Override
    public List<DbSubjectDto> findNotTakenByFacultyName(String facultyName) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_NOT_TAKEN_BY_FACULTY_NAME)){
            statement.setString(1, facultyName);
            return parseSubjects(statement.executeQuery());
        }
    }


    private static List<DbSubjectDto> parseSubjects(ResultSet rs) throws SQLException {
        List<DbSubjectDto> subjects = new ArrayList<>();
        while (rs.next()){
            subjects.add(parseSubject(rs));
        }
        return subjects;
    }

    private static DbSubjectDto parseSubject(ResultSet rs) throws SQLException{
        int id = rs.getInt(1);
        String name = rs.getString(2);
        return new DbSubjectDto(id, name);
    }
}
