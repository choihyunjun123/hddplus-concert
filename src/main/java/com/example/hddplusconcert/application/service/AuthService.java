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

        return tokenProvider.generateToken(user.getId().toString());
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
            queueManager.dequeue(user.getId().toString());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String tokenUserId = tokenProvider.getIdFromToken(token);

        // 토큰의 사용자 ID와 요청의 사용자 ID가 일치하는지 검증
        if (!tokenUserId.equals(user.getId().toString())) {
            queueManager.dequeue(tokenUserId);
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    private User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
