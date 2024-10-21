package com.example.hddplusconcert.application.port.in;

import java.math.BigDecimal;

public interface PaymentUseCase {
    boolean makePayment(String userId, Long seatNumber, String concertId, BigDecimal amount);
}
