package com.example.InspectionBoard.model.entity;

import com.example.InspectionBoard.model.enums.AccountRole;

import java.util.Objects;

public class Account {
    private final AccountRole role;
    private final String login;
    private final boolean isBlocked;

    public Account(AccountRole role, String login, boolean isBlocked) {
        this.role = role;
        this.login = login;
        this.isBlocked = isBlocked;
    }

    public AccountRole getRole() {
        return role;
    }

    public String getLogin() {
        return login;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return getRole() == account.getRole() && getLogin().equals(account.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRole(), getLogin());
    }

    @Override
    public String toString() {
        return "Account{" +
                "role=" + role +
                ", login='" + login + '\'' +
                '}';
    }
}
