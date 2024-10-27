package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.out.UserRepository;
import com.example.hddplusconcert.common.exception.CustomException;
import com.example.hddplusconcert.common.exception.ErrorCode;
import com.example.hddplusconcert.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class BalanceServiceTest {

    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private UserRepository userRepository;

    private final String userId = "testUserId";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("유저가 없으면 예외를 던진다")
    void chargeBalance_UserNotFound() {
        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());
        BigDecimal amount = BigDecimal.ONE;

        CustomException exception = assertThrows(CustomException.class,
                () -> balanceService.chargeBalance(userId, amount));

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("충전 잔액이 0보다 작으면 예외를 던진다")
    void chargeBalance_InsufficientBalance() {
        User user = User.createNewUser(userId);
        BigDecimal amount = BigDecimal.valueOf(-1);

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));

        CustomException exception = assertThrows(CustomException.class,
                () -> balanceService.chargeBalance(userId, amount));

        assertEquals(ErrorCode.NEGATIVE_BALANCE, exception.getErrorCode());
    }

    @Test
    @DisplayName("충전 성공")
    void chargeBalance_Success() {
        User user = User.createNewUser(userId);
        BigDecimal amount = BigDecimal.ONE;

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));

        User charedUser = balanceService.chargeBalance(userId, amount);

        assertEquals(user, charedUser);
    }

}