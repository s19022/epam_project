package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.SubjectDao;
import com.example.InspectionBoard.model.dto.db.DbSubjectDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCSubjectDao implements SubjectDao {
    private static final String GET_ALL_SUBJECTS =  "SELECT id, name FROM subject";

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
