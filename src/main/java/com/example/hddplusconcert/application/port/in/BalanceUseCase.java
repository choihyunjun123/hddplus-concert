package com.example.hddplusconcert.application.port.in;

import com.example.hddplusconcert.domain.model.User;

import java.math.BigDecimal;

public interface BalanceUseCase {
    // 잔액 충전
    User chargeBalance(String userId, BigDecimal amount);
}
