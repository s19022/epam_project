package com.example.InspectionBoard.model.dao.implementation;

import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JDBCFactory extends DaoFactory {
    private static final Logger LOGGER = LogManager.getLogger(JDBCFactory.class.getName());
    private final DataSource dataSource;
    private final AccountDao accountDao = JDBCAccountDao.getInstance();
    private final FacultyDao facultyDao = JBDCFacultyDao.getInstance();
    private final SubjectDao subjectDao = JDBCSubjectDao.getInstance();

    public JDBCFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public AccountDao createAccountDao() {
        return accountDao;
    }

    @Override
    public FacultyDao createFacultyDao() {
        return facultyDao;
    }

    @Override
    public SubjectDao createSubjectDao() {
        return subjectDao;
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
