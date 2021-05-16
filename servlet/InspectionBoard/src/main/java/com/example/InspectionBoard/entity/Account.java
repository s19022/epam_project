package com.example.InspectionBoard.entity;


import java.util.Objects;

public class Account {
    private final int id;
    private final AccountRole role;

    public Account(int id, AccountRole role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public AccountRole getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && role == account.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}
