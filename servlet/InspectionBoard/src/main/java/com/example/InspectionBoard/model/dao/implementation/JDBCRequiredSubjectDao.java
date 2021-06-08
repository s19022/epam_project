package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.RequiredSubjectDao;
import com.example.InspectionBoard.model.dto.CreateFacultySubjectDto;
import com.example.InspectionBoard.model.dto.db.DbRequiredSubjectDto;
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

    private static final String CREATE_SUBJECT ="INSERT INTO required_subject(faculty_id, subject_id, minimal_grade) values" +
            " ((SELECT f.id from faculty f where f.name = ?), (SELECT s.id from subject s where s.name = ?), ?)";


    private final Connection connection;

    public JDBCRequiredSubjectDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<DbRequiredSubjectDto> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public List<DbRequiredSubjectDto> getAllByFacultyId(int facultyId) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_FACULTY_ID)){
            statement.setInt(1, facultyId);
            ResultSet rs = statement.executeQuery();
            return parseRequiredSubjects(rs);
        }
    }

    @Override
    public void create(CreateFacultySubjectDto dto) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(CREATE_SUBJECT)){
            statement.setString(1, dto.getFacultyName());
            statement.setString(2, dto.getSubjectName());
            statement.setInt(3, dto.getMinimalMark());
            statement.executeUpdate();
        }
    }

    private List<DbRequiredSubjectDto> parseRequiredSubjects(ResultSet rs) throws SQLException{
        List<DbRequiredSubjectDto> list = new ArrayList<>();
        while (rs.next()){
            list.add(parseRequiredSubject(rs));
        }
        return list;
    }

    private DbRequiredSubjectDto parseRequiredSubject(ResultSet rs) throws SQLException{
        int subjectId = rs.getInt(1);
        String subjectName = rs.getString(2);
        int grade = rs.getInt(3);
        return new DbRequiredSubjectDto(subjectId, subjectName, grade);
    }
}
