package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.AccountIsBlockedException;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import com.example.InspectionBoard.model.dao.AccountDao;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dto.SaveEnrolleeDto;
import com.example.InspectionBoard.model.entity.Account;
import com.example.InspectionBoard.model.mockedClasses.MockAccountDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

import static com.example.InspectionBoard.model.enums.AccountRole.ADMIN;
import static com.example.InspectionBoard.model.enums.AccountRole.ENROLLEE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {
    private static final DaoFactory factoryMock = mock(DaoFactory.class);
    @BeforeAll
    static void beforeAll() throws NoSuchFieldException, IllegalAccessException {
        when(factoryMock.createAccountDao(any(Connection.class))).thenReturn(new MockAccountDao());
        Field factoryField = DaoFactory.class.getDeclaredField("daoFactory");
        factoryField.setAccessible(true);
        factoryField.set(null, factoryMock);
    }

    @Test
    void wrongLogin() {
        when(factoryMock.createAccountDao()).thenReturn(new MockAccountDao());
        assertThrows(WrongLoginPasswordException.class, () -> new AccountService().getAccount("wrongLogin", "enrollee2"));
    }

    @Test
    void wrongPassword() {
        when(factoryMock.createAccountDao()).thenReturn(new MockAccountDao());
        assertThrows(WrongLoginPasswordException.class, () -> new AccountService().getAccount("enrollee2", "wrongPassword"));
    }

    @Test
    void getBlockedAccount(){
        when(factoryMock.createAccountDao()).thenReturn(new MockAccountDao());
        assertThrows(AccountIsBlockedException.class, () -> new AccountService().getAccount("enrollee1Blocked", "enrollee1Blocked"));
    }

    @Test
    void getEnrolleeAccount() throws WrongLoginPasswordException, AccountIsBlockedException {
        when(factoryMock.createAccountDao()).thenReturn(new MockAccountDao());
        Account account = new AccountService().getAccount("enrollee2", "enrollee2");
        assertEquals(ENROLLEE, account.getRole());
    }

    @Test
    void getAdminAccount() throws WrongLoginPasswordException, AccountIsBlockedException {
        when(factoryMock.createAccountDao()).thenReturn(new MockAccountDao());
        Account account = new AccountService().getAccount("admin3", "admin3");
        assertEquals(ADMIN, account.getRole());
    }

    @Test
    void createEnrollee() throws SQLException {
        AccountDao dao = mock(AccountDao.class);
        doThrow(new SQLException()).when(dao).insertEnrollee(any(SaveEnrolleeDto.class), any(Integer.class));
        when(factoryMock.createAccountDao()).thenReturn(dao);
        new AccountService().createEnrollee(SaveEnrolleeDto.getInstance("e1", "e1","e1", "e1","e1", "e1","e1", "e1","e1", new byte[]{}));
    }
}