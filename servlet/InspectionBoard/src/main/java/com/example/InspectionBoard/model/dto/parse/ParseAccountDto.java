package com.example.InspectionBoard.model.dto.parse;

import com.example.InspectionBoard.model.enums.AccountRole;

public class ParseAccountDto {
    private final int id;
    private final AccountRole role;
    private final boolean isBlocked;

    public ParseAccountDto(int id, AccountRole role, boolean isBlocked) {
        this.id = id;
        this.role = role;
        this.isBlocked = isBlocked;
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
}
