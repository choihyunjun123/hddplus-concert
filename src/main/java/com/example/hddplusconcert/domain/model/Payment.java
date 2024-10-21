package com.example.hddplusconcert.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long paymentId;
    private String userId;
    private Long seatNumber;
    private String concertId;
    private BigDecimal amount;
    private LocalDateTime paymentTime;

    public Payment(String userId, Long seatNumber, String concertId, BigDecimal amount) {
        this.userId = userId;
        this.seatNumber = seatNumber;
        this.concertId = concertId;
        this.amount = amount;
        this.paymentTime = LocalDateTime.now();
    }
}
