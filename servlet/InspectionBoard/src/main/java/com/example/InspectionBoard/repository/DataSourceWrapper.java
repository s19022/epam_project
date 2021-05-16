package com.example.InspectionBoard.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceWrapper {
    private static final Object LOCK = new Object();
    private static final Logger LOGGER = LogManager.getLogger(DataSourceWrapper.class.getName());

    private static DataSource dataSource;


    public static DataSource getDataSource(){
        if (dataSource == null){
            synchronized (LOCK){
                if (dataSource == null){
                    try{
                        Context init = new InitialContext();
                        Context context = (Context) init.lookup("java:comp/env");
                        dataSource = (DataSource) context.lookup("jdbc/postgres");
                        LOGGER.info("Successfully found data source");
                    }catch (NamingException ex){
                        LOGGER.error("JDBC loading", ex);
                        System.exit(-1); //couldn't start application
                    }
                }
            }
        }
        return dataSource;
    }

    private static synchronized void init(){

    }

}
