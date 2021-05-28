package com.example.InspectionBoard.model.dto.db;

import com.example.InspectionBoard.model.enums.AccountRole;

public class DbParseAccountDto {
    private final int id;
    private final AccountRole role;
    private final boolean isBlocked;
    private final String login;

    public DbParseAccountDto(int id, AccountRole role, boolean isBlocked, String login) {
        this.id = id;
        this.role = role;
        this.isBlocked = isBlocked;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public AccountRole getRole() {
        return role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public String getLogin() {
        return login;
    }
}
