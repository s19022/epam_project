package com.example.inspectionboard.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum AccountType implements GrantedAuthority {
    ADMIN,
    ENROLLEE;

    @Override
    public String getAuthority() {
        return name();
    }
}
