package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.SubjectDao;
import com.example.InspectionBoard.model.entity.Subject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class JDBCSubjectDao implements SubjectDao {
    private static final Logger LOGGER = LogManager.getLogger(JDBCSubjectDao.class.getName());
    private static final String GET_ALL_SUBJECTS =  "SELECT id, name FROM subject";
    private final DataSource dataSource;

    public JDBCSubjectDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int create(Subject subject) {
        throw new NotImplementedException();
    }

    @Override
    public int update(Subject subject) {
        throw new NotImplementedException();
    }

    @Override
    public boolean delete(int id) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Subject> findById(int id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Subject> findAll(){
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_SUBJECTS)){
            return parseSubjects(rs);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
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
}
