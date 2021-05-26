package com.example.InspectionBoard.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface GenericDao<T> extends AutoCloseable{
    int create(T t);
    int update(T t);
    boolean delete(int id);
    Optional<T> findById(int id);
    List<T> findAll() throws SQLException;
    Connection getConnection();

    @Override
    default void close() throws SQLException {
        getConnection().close();
    }
}
