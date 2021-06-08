package com.example.InspectionBoard.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> extends AutoCloseable{
    Connection getConnection();

    @Override
    default void close() throws SQLException {
        getConnection().close();
    }
}
