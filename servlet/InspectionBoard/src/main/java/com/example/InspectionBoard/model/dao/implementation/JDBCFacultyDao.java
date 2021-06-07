package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.FacultyDao;
import com.example.InspectionBoard.model.dto.CreateFacultyDto;
import com.example.InspectionBoard.model.dto.ModifyFacultyDto;
import com.example.InspectionBoard.model.dto.db.DbFacultyDto;
import com.example.InspectionBoard.model.dto.db.DbRequiredSubjectDto;

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
    private static final String SUBTRACT_BUDGET_PLACE =
            "UPDATE faculty " +
            "SET all_places = (all_places - 1), budget_places = (budget_places - 1) " +
            "WHERE name = ?";

    private static final String SUBTRACT_CONTRACT_PLACE =
            "UPDATE faculty " +
            "SET all_places = (all_places - 1) " +
            "WHERE name = ?";

    private static final String UPDATE_FACULTY =
            "UPDATE faculty SET budget_places = ?, all_places = ? WHERE name = ?";

    private static final String CREATE = "insert into faculty(name, budget_places, all_places) values (?, ?, ?) ";
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
    public void subtractBudgetPlace(String facultyName) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(SUBTRACT_BUDGET_PLACE)){
            statement.setString(1, facultyName);
            statement.executeUpdate();
        }
    }

    @Override
    public void subtractContractPlace(String facultyName) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(SUBTRACT_CONTRACT_PLACE)){
            statement.setString(1, facultyName);
            statement.executeUpdate();
        }
    }

    @Override
    public void create(CreateFacultyDto dto) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(CREATE)){
            statement.setString(1, dto.getName());
            statement.setInt(2, dto.getBudgetPlaces());
            statement.setInt(3, dto.getAllPlaces());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(ModifyFacultyDto dto) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_FACULTY)){
            statement.setInt(1, dto.getBudgetPlaces());
            statement.setInt(2, dto.getAllPlaces());
            statement.setString(3, dto.getName());
            statement.executeUpdate();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    private List<DbFacultyDto> parseFaculties(ResultSet rs) throws SQLException {
        List<DbFacultyDto> faculties = new ArrayList<>();
        while (rs.next()){
            faculties.add(parseFaculty(rs));
        }
        return faculties;
    }

    private DbFacultyDto parseFaculty(ResultSet rs) throws SQLException{
        int id = rs.getInt(1);
        String name = rs.getString(2);
        int budgetPlaces = rs.getInt(3);
        int allPlaces = rs.getInt(4);
        List<DbRequiredSubjectDto> requiredSubjects = DaoFactory.getInstance().createRequiredSubjectDao(connection).getAllByFacultyId(id);

        return new DbFacultyDto(id, name, budgetPlaces, allPlaces, requiredSubjects);
    }

    private List<DbFacultyDto> executeQuery(String query) throws SQLException{
        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)){
            return parseFaculties(rs);
        }
    }
}
