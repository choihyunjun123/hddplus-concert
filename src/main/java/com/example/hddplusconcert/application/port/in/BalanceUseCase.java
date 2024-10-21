package com.example.hddplusconcert.application.port.in;

import java.math.BigDecimal;

public interface BalanceUseCase {
    // 잔액 충전
    void rechargeBalance(String userId, BigDecimal amount);

    // 잔액 조회
    BigDecimal getBalance(String userId);
}
