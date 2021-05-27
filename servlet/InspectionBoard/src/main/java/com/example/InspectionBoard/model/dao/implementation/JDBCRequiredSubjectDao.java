package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.RequiredSubjectDao;
import com.example.InspectionBoard.model.entity.RequiredSubject;
import com.example.InspectionBoard.model.entity.Subject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCRequiredSubjectDao implements RequiredSubjectDao {
    private static final String GET_ALL_BY_FACULTY_ID = "SELECT s.id, s.name, r_s.minimal_grade " +
                                                        "FROM subject s, required_subject r_s " +
                                                        "WHERE faculty_id = ? and s.id = r_s.subject_id";

    private final Connection connection;

    public JDBCRequiredSubjectDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<RequiredSubject> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public List<RequiredSubject> getAllByFacultyId(int facultyId) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_FACULTY_ID)){
            statement.setInt(1, facultyId);
            ResultSet rs = statement.executeQuery();
            return parseRequiredSubjects(rs);
        }
    }

    private List<RequiredSubject> parseRequiredSubjects(ResultSet rs) throws SQLException{
        List<RequiredSubject> list = new ArrayList<>();
        while (rs.next()){
            list.add(parseRequiredSubject(rs));
        }
        return list;
    }

    private RequiredSubject parseRequiredSubject(ResultSet rs) throws SQLException{
        int subjectId = rs.getInt(1);
        String subjectName = rs.getString(2);
        int grade = rs.getInt(3);
        return new RequiredSubject(new Subject(subjectId, subjectName), grade);
    }
}
