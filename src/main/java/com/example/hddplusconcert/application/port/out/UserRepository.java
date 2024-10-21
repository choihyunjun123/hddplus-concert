package com.example.hddplusconcert.application.port.out;

import com.example.hddplusconcert.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String userId);

    void save(User user);

    List<User> findAll();
}
