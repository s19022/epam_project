package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.EnrolleeSubjectDao;
import com.example.InspectionBoard.model.dto.parse.ParseEnrolleeSubjectDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//fixme
public class JDBCEnrolleeSubjectDao implements EnrolleeSubjectDao {
    private static final String FIND_ALL_BY_ENROLLEE_LOGIN =    "SELECT m.subject_id, m.mark " +
                                                                "FROM mark m " +
                                                                "WHERE m.enrollee_id = ? " +
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
    public List<ParseEnrolleeSubjectDto> findAll() throws SQLException {
        return null;
    }

    @Override
    public List<ParseEnrolleeSubjectDto> getAllByEnrolleeId(int id) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_ENROLLEE_LOGIN)){
            statement.setInt(1, id);
            return parseSubjects(statement.executeQuery());
        }
    }

    private static List<ParseEnrolleeSubjectDto> parseSubjects(ResultSet rs) throws SQLException {
        List<ParseEnrolleeSubjectDto> out = new ArrayList<>();
        while (rs.next()){
            out.add(parseSubject(rs));
        }
        return out;
    }

    private static ParseEnrolleeSubjectDto parseSubject(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        int mark = rs.getInt(2);
        return new ParseEnrolleeSubjectDto(id, mark);
    }
}
