package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.model.dao.DataSourceWrapper;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.exceptions.ParsingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JBDCFacultyDao {
    private static final Logger LOGGER = LogManager.getLogger(JBDCFacultyDao.class.getName());

    private static final String FIND_ALL_FACULTIES =  "SELECT id, name, budget_places, all_places FROM faculty";

    private static final Object LOCK = new Object();
    private static JBDCFacultyDao instance;

    private final DataSource dataSource;

    private JBDCFacultyDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Faculty> getAllFaculties() throws ParsingException {
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(FIND_ALL_FACULTIES)){
            return parseFaculties(rs);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new ParsingException(ex.getMessage(), ex);
        }
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
        return new Faculty(id, name, budgetPlaces, allPlaces);
    }


    public static JBDCFacultyDao getInstance() {
        if (instance == null){
            synchronized (LOCK){
                if (instance == null){
                    instance = new JBDCFacultyDao(DataSourceWrapper.getDataSource());
                }
            }
        }
        return instance;
    }


}
