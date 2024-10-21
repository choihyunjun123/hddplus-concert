package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.in.UserUseCase;
import com.example.hddplusconcert.application.port.out.UserRepository;
import com.example.hddplusconcert.common.exception.CustomException;
import com.example.hddplusconcert.common.exception.ErrorCode;
import com.example.hddplusconcert.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(String userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    throw new CustomException(ErrorCode.USER_ALREADY_EXIST);
                });
        User newUser = User.createNewUser(userId);
        userRepository.save(newUser);
    }

    @Override
    public User findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}