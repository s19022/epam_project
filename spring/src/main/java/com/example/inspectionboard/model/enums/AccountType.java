package com.example.inspectionboard.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum AccountType implements GrantedAuthority {
    ADMIN,
    ENROLLEE,
    UNKNOWN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
