package com.example.springmodels.models;

import org.springframework.security.core.GrantedAuthority;

public enum roleEnum implements GrantedAuthority {
    USER, ADMIN, MODER;

    @Override
    public String getAuthority()
    {
        return name();
    }
}
