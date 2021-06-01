package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCFactory extends DaoFactory {
    private static final Logger LOGGER = LogManager.getLogger(JDBCFactory.class.getName());
    private final DataSource dataSource;
    private JDBCFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection(){
        try{
            return dataSource.getConnection();
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    @Override
    public AccountDao createAccountDao(Connection connection) {
        return new JDBCAccountDao(connection);
    }

    @Override
    public FacultyDao createFacultyDao(Connection connection) {
        return new JDBCFacultyDao(connection);
    }

    @Override
    public SubjectDao createSubjectDao(Connection connection) {
        return new JDBCSubjectDao(connection);
    }

    @Override
    public RequiredSubjectDao createRequiredSubjectDao(Connection connection) {
        return new JDBCRequiredSubjectDao(connection);
    }

    @Override
    public EnrolleeSubjectDao createEnrolleeSubjectDao(Connection connection) {
        return new JDBCEnrolleeSubjectDao(connection);
    }

    @Override
    public FacultyRegistrationDao createFacultyRegistrationDao(Connection connection) {
        return new JDBCFacultyRegistrationDao(connection);
    }

    @Override
    public EnrolleeDao createEnrolleeDao(Connection connection) {
        return new JDBCEnrolleeDao(connection);
    }

    public static JDBCFactory getInstance(){
        try{
            Context init = new InitialContext();
            Context context = (Context) init.lookup("java:comp/env");
            DataSource dataSource = (DataSource) context.lookup("jdbc/postgres");
            LOGGER.info("Successfully found data source");
            return new JDBCFactory(dataSource);
        }catch (NamingException ex){
            LOGGER.error("JDBC loading", ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
}
