package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

public class JDBCFactory extends DaoFactory {
    private static final Logger LOGGER = LogManager.getLogger(JDBCFactory.class.getName());
    private final DataSource dataSource;
    public JDBCFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public AccountDao createAccountDao() {
        try{
            return new JDBCAccountDao(dataSource.getConnection());
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    @Override
    public FacultyDao createFacultyDao() {
        try{
            return new JDBCFacultyDao(dataSource.getConnection());
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    @Override
    public SubjectDao createSubjectDao() {
        try{
            return new JDBCSubjectDao(dataSource.getConnection());
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    @Override
    public RequiredSubjectDao createRequiredSubjectDao() {
        try{
            return new JDBCRequiredSubjectDao(dataSource.getConnection());
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
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
