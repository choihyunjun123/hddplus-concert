package com.example.hddplusconcert.application.port.out;

import com.example.hddplusconcert.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUserId(String userId);

    User save(User user);

    List<User> findAll();

    long countByUserId(String userId);
}
