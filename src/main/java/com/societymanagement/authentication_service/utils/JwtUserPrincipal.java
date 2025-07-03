package com.societymanagement.authentication_service.utils;

import lombok.Data;

import java.util.List;
@Data
public class JwtUserPrincipal {
    private final Long userId;
    private final Long societyId;

    public JwtUserPrincipal(Long userId, Long societyId) {
        this.userId = userId;
        this.societyId = societyId;
    }
}