package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.in.AuthUseCase;
import com.example.hddplusconcert.application.port.out.QueueManager;
import com.example.hddplusconcert.application.port.out.TokenProvider;
import com.example.hddplusconcert.application.port.out.UserRepository;
import com.example.hddplusconcert.common.exception.CustomException;
import com.example.hddplusconcert.common.exception.ErrorCode;
import com.example.hddplusconcert.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthUseCase {

    private final QueueManager queueManager;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public AuthService(QueueManager queueManager, TokenProvider tokenProvider, UserRepository userRepository) {
        this.queueManager = queueManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public String generateToken(String userId) {
        User user = getUserByUserId(userId);
        queueManager.enqueue(user.getId().toString());
        Long position = queueManager.getPosition(user.getId().toString());
        return tokenProvider.generateToken(userId, position);
    }

    @Override
    public Long getQueuePosition(String userId) {
        User user = getUserByUserId(userId);
        return queueManager.getPosition(user.getId().toString());
    }

    @Override
    public void validateAccessRights(String token, String userId) {
        User user = getUserByUserId(userId);

        if (!tokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        Long position = queueManager.getPosition(user.getId().toString());
        if (position != 1L) {
            throw new CustomException(ErrorCode.USER_NOT_IN_TURN);
        }
    }

    private User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
