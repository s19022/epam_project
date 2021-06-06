package com.example.InspectionBoard.model.dao;

import com.example.InspectionBoard.model.dao.implementation.JDBCAccountDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


class AccountDaoTest {
    private static DataSource dataSource;
    private Connection connection;
    @BeforeAll
    static void beforeAll(){
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName("jdbc/postgres");
        source.setURL("jdbc:postgresql://localhost:5432/inspection_board_test");
        source.setUser("epam_project");
        source.setPassword("root");
        source.setMaxConnections(1);

        dataSource = source;
    }

    @BeforeEach
    void setUp() throws SQLException {
        connection = dataSource.getConnection();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void findByLoginAndPassword() throws SQLException {
        System.out.println(new JDBCAccountDao(connection).findByLogin("e2").orElseThrow(RuntimeException::new));
    }

    @Test
    void blockEnrollee() {
    }

    @Test
    void unblockEnrollee() {
    }

    @Test
    void insertEnrollee() {
    }

    @Test
    void insertAccount() {
    }

    @Test
    void findByLogin() {
    }
}