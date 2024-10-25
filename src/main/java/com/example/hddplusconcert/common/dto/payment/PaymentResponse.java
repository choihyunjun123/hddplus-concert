package com.example.hddplusconcert.common.dto.payment;

import com.example.hddplusconcert.domain.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        String userId,
        Long seatNumber,
        Long concertId,
        BigDecimal amount,
        LocalDateTime paymentTime) {

    public static PaymentResponse fromDomainModel(Payment payment) {
        return new PaymentResponse(
                payment.getUserId(),
                payment.getSeatNumber(),
                payment.getConcertId(),
                payment.getAmount(),
                payment.getPaymentTime());
    }
}
