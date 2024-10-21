package com.example.hddplusconcert.application.port.in;

import com.example.hddplusconcert.domain.model.User;

import java.util.List;

public interface UserUseCase {
    // 회원가입
    void register(String userId);

    User findById(String userId);

    List<User> findAll();
}
