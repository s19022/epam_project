package com.example.InspectionBoard.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface GenericDao<T>{
    int create(T t);
    int update(T t);
    boolean delete(int id);
    Optional<T> findById(int id);
    List<T> findAll();

    default void handleException(SQLException ex) throws SQLExceptionWrapper{
        LogManager.getLogger(this.getClass().getName()).error(ex);
        //todo change to abstract getLogger
        throw new SQLExceptionWrapper(ex);
    }
}
