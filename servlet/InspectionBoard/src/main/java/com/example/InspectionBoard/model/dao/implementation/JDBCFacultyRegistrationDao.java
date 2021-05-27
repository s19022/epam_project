package com.example.InspectionBoard.model.dao.implementation;

import java.sql.Connection;

public class JDBCFacultyRegistrationDao {
    private final Connection connection;

    public JDBCFacultyRegistrationDao (Connection connection) {
        this.connection = connection;
    }


}
