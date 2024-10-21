package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.in.AuthUseCase;
import com.example.hddplusconcert.application.port.out.QueueManager;
import com.example.hddplusconcert.application.port.out.TokenProvider;
import com.example.hddplusconcert.application.port.out.UserRepository;
import com.example.hddplusconcert.common.exception.CustomException;
import com.example.hddplusconcert.common.exception.ErrorCode;
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
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        queueManager.enqueue(userId);
        Long position = queueManager.getPosition(userId);
        return tokenProvider.generateToken(userId, position);
    }

    @Override
    public Long getQueuePosition(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return queueManager.getPosition(userId);
    }

    @Override
    public void validateTokenAndQueuePosition(String token, String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!tokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        Long queuePosition = queueManager.getPosition(userId);
        if (queuePosition != 1L) {
            throw new CustomException(ErrorCode.USER_NOT_IN_TURN);
        }
    }
}
