package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.FacultyDao;
import com.example.InspectionBoard.model.dto.db.DbFacultyDto;
import com.example.InspectionBoard.model.dto.db.DbRequiredSubjectDto;
import com.example.InspectionBoard.model.service.RequiredSubjectService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCFacultyDao implements FacultyDao {
    private static final String FIND_ALL = "SELECT id, name, budget_places, all_places, deleted" +
            " FROM faculty " +
            " WHERE deleted = false ";
    private static final String FIND_BY_NAME = FIND_ALL + " and name = ? FOR UPDATE ";
    private static final String DELETE_BY_FACULTY_NAME = "UPDATE faculty SET deleted = true WHERE name = ?";
    private static final String FIND_ALL_ORDER_BY_NAME_ASC = FIND_ALL + " ORDER by name asc";
    private static final String FIND_ALL_ORDER_BY_NAME_DESC =FIND_ALL + " ORDER by name desc";
    private static final String FIND_ALL_ORDER_BY_BUDGET_PLACES_DESC = FIND_ALL + " ORDER by budget_places desc";
    private static final String FIND_ALL_ORDER_BY_ALL_PLACES_DESC = FIND_ALL + " ORDER by all_places desc";
    private static final String FIND_BY_ENROLLEE_LOGIN =
            "SELECT f.id, f.name, f.all_places, f.budget_places " +
            "FROM faculty f, registration r, account a " +
            "WHERE a.login = ? AND a.id = r.enrollee_id AND r.faculty_id = f.id and f.deleted = false ";
    private final Connection connection;

    public JDBCFacultyDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<DbFacultyDto> findByName(String name) throws SQLException {
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
    public void deleteByFacultyName(String facultyName) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(DELETE_BY_FACULTY_NAME)){
            statement.setString(1, facultyName);
            statement.executeUpdate();
        }
    }

    @Override
    public List<DbFacultyDto> findAll() throws SQLException {
        return executeQuery(FIND_ALL);
    }

    @Override
    public List<DbFacultyDto> findAllOrderByNameDesc() throws SQLException {
        return executeQuery(FIND_ALL_ORDER_BY_NAME_DESC);
    }

    @Override
    public List<DbFacultyDto> findAllOrderByNameAsc() throws SQLException {
        return executeQuery(FIND_ALL_ORDER_BY_NAME_ASC);
    }

    @Override
    public List<DbFacultyDto> findAllOrderByBudgetPlacesDesc() throws SQLException {
        return executeQuery(FIND_ALL_ORDER_BY_BUDGET_PLACES_DESC);
    }

    @Override
    public List<DbFacultyDto> findAllOrderByAllPlacesDesc() throws SQLException {
        return executeQuery(FIND_ALL_ORDER_BY_ALL_PLACES_DESC);
    }

    @Override
    public List<DbFacultyDto> findByEnrolleeLoginEquals(String login) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_BY_ENROLLEE_LOGIN)){
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            return parseFaculties(rs);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    private static List<DbFacultyDto> parseFaculties(ResultSet rs) throws SQLException {
        List<DbFacultyDto> faculties = new ArrayList<>();
        while (rs.next()){
            faculties.add(parseFaculty(rs));
        }
        return faculties;
    }

    private static DbFacultyDto parseFaculty(ResultSet rs) throws SQLException{
        int id = rs.getInt(1);
        String name = rs.getString(2);
        int budgetPlaces = rs.getInt(3);
        int allPlaces = rs.getInt(4);
        List<DbRequiredSubjectDto> requiredSubjects = RequiredSubjectService.getAllByFacultyId(id);
        return new DbFacultyDto(id, name, budgetPlaces, allPlaces, requiredSubjects);
    }

    private List<DbFacultyDto> executeQuery(String query) throws SQLException{
        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)){
            return parseFaculties(rs);
        }
    }
}
