package com.example.hddplusconcert.application.port.in;

import com.example.hddplusconcert.domain.model.Payment;

import java.math.BigDecimal;

public interface PaymentUseCase {
    Payment makePayment(String userId, Long seatNumber, Long concertId, BigDecimal amount);
}
