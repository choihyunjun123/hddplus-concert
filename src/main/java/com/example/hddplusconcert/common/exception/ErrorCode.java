package com.example.hddplusconcert.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 토큰 관련 오류
    INVALID_TOKEN("Invalid token"),
    SERVER_ERROR("Server error"),

    // 잔액 관련 오류
    INSUFFICIENT_BALANCE("Insufficient balance"),

    // 좌석 관련 오류
    SEAT_NOT_AVAILABLE("Seat not available"),
    SEAT_NOT_HELD_BY_USER("Seat is not held by the user"),
    SEAT_NOT_FOUND("Seat not found"),

    // 유저 관련 오류
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXIST("User already exist"),

    // 콘서트 관련 오류
    CONCERT_NOT_FOUND("Concert not found"),
    CONCERT_ALREADY_EXIST("Concert already exist"),

    // 대기열 관련 오류
    USER_NOT_IN_TURN("User not in turn");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
