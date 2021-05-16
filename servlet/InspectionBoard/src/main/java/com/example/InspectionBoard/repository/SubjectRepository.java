package com.example.InspectionBoard.repository;

import com.example.InspectionBoard.entity.Subject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exceptions.ParsingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SubjectRepository {
    private static final Logger LOGGER = LogManager.getLogger(SubjectRepository.class.getName());
    private static final String GET_ALL_SUBJECTS =  "SELECT id, name FROM subject";
    private final DataSource dataSource;

    private SubjectRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Subject> getSubjects() throws ParsingException {
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_SUBJECTS)){
            return parseSubjects(rs);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new ParsingException(ex.getMessage(), ex);
        }
    }

    private static List<Subject> parseSubjects(ResultSet rs) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        while (rs.next()){
            subjects.add(parseSubject(rs));
        }
        return subjects;
    }

    private static Subject parseSubject(ResultSet rs) throws SQLException{
        int id = rs.getInt(1);
        String name = rs.getString(2);
        return new Subject(id, name);
    }

    public static SubjectRepository getInstance(){
        return new SubjectRepository(DataSourceWrapper.getDataSource());
    }
}
