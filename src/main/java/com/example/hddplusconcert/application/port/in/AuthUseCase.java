package com.example.hddplusconcert.application.port.in;

import com.example.hddplusconcert.domain.model.User;

public interface AuthUseCase {
    // userId로 토큰 생성
    String generateToken(String userId);

     // 유저의 대기열 위치 반환
    Long getQueuePosition(String userId);

    // 토큰 검증 및 순번 조회
    void validateTokenAndQueuePosition(String token, String userId);
}
