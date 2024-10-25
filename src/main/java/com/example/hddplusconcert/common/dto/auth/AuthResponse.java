package com.example.hddplusconcert.common.dto.auth;

public record AuthResponse(String token) {

    public static AuthResponse fromDomainModel(String token) {
        return new AuthResponse(token);
    }
}
