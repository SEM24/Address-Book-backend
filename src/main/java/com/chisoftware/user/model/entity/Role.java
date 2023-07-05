package com.chisoftware.user.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER;
    private final String roleName = "ROLE_" + name();

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
