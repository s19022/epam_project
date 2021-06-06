package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.AccountIsBlockedException;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.entity.Account;
import com.example.InspectionBoard.model.mockedClasses.MockAccountDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

import static com.example.InspectionBoard.model.enums.AccountRole.ENROLLEE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    @BeforeAll
    static void beforeAll() throws SQLException, NoSuchFieldException, IllegalAccessException {
        DaoFactory factoryMock = mock(DaoFactory.class);
        when(factoryMock.createAccountDao(any(Connection.class))).thenReturn(new MockAccountDao());
        when(factoryMock.createAccountDao()).thenReturn(new MockAccountDao());
        Field factoryField = DaoFactory.class.getDeclaredField("daoFactory");
        factoryField.setAccessible(true);
        factoryField.set(null, factoryMock);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void wrongLogin() {
        assertThrows(WrongLoginPasswordException.class, () -> new AccountService().getAccount("wrongLogin", "enrollee2"));
    }

    @Test
    void wrongPassword() {
        assertThrows(WrongLoginPasswordException.class, () -> new AccountService().getAccount("enrollee2", "wrongPassword"));
    }

    @Test
    void getBlockedAccount(){
        assertThrows(AccountIsBlockedException.class, () -> new AccountService().getAccount("enrollee1Blocked", "enrollee1Blocked"));
    }

    @Test
    void getEnrolleeAccount() throws WrongLoginPasswordException, AccountIsBlockedException {
        Account account = new AccountService().getAccount("enrollee2", "enrollee2");
        assertEquals(ENROLLEE, account.getRole());
    }


    @Test
    void createEnrollee() {
    }

    @Test
    void blockEnrollee() {
    }

    @Test
    void unblockEnrollee() {
    }
}