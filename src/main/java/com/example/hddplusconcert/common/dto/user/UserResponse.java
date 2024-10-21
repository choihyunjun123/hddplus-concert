package com.example.hddplusconcert.common.dto.user;

import com.example.hddplusconcert.domain.model.User;

import java.math.BigDecimal;

public record UserResponse(String userId, BigDecimal balance) {

    public static UserResponse fromDomainModel(User user) {
        return new UserResponse(user.getUserId(), user.getBalance());
    }
}
