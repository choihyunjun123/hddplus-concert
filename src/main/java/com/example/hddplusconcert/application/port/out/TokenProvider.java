package com.example.hddplusconcert.application.port.out;

// 토큰 생성, 관리
public interface TokenProvider {
    // userId, 대기열 위치로 토큰 생성
    String generateToken(String id, Long queuePosition);

    // 토큰의 유효성 검증
    boolean validateToken(String token);

    // 토큰에서 userId 추출
    String getUserIdFromToken(String token);
}
