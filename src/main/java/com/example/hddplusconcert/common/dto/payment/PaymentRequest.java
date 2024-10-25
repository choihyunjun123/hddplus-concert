package com.example.hddplusconcert.common.dto.payment;

import java.math.BigDecimal;

public record PaymentRequest(
        Long seatNumber,
        Long concertId,
        BigDecimal amount) {
}
