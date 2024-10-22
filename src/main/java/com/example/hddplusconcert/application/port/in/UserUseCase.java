package com.example.hddplusconcert.application.port.in;

import com.example.hddplusconcert.domain.model.User;

import java.util.List;

public interface UserUseCase {
    // 회원가입
    User register(String userId);

    User findByUserId(String userId);

    List<User> findAll();
}
