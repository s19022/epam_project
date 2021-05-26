package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.FacultyDao;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.entity.RequiredSubject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCFacultyDao implements FacultyDao {
    private static final String FIND_ALL_FACULTIES = "SELECT id, name, budget_places, all_places FROM faculty";
    private static final String FIND_BY_NAME = FIND_ALL_FACULTIES + " WHERE name = ?";

    private final Connection connection;

    public JDBCFacultyDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int create(Faculty faculty) {
        throw new NotImplementedException();
    }

    @Override
    public int update(Faculty faculty) {
        throw new NotImplementedException();
    }

    @Override
    public boolean delete(int id) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Faculty> findById(int id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Faculty> findAll() throws SQLException {
        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(FIND_ALL_FACULTIES)){
            return parseFaculties(rs);
        }
    }

    @Override
    public Optional<Faculty> getByName(String name) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)){
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return Optional.of(parseFaculty(rs));
            }
            return Optional.empty();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    private static List<Faculty> parseFaculties(ResultSet rs) throws SQLException {
        List<Faculty> faculties = new ArrayList<>();
        while (rs.next()){
            faculties.add(parseFaculty(rs));
        }
        return faculties;
    }

    private static Faculty parseFaculty(ResultSet rs) throws SQLException{
        int id = rs.getInt(1);
        String name = rs.getString(2);
        int budgetPlaces = rs.getInt(3);
        int allPlaces = rs.getInt(4);
        List<RequiredSubject> requiredSubjects =
                DaoFactory.getInstance().createRequiredSubjectDao().getAllByFacultyId(id);
        return new Faculty(id, name, budgetPlaces, allPlaces, requiredSubjects);
    }
}
