package com.example.hddplusconcert.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentRequest {
    private String userId;
    private Long seatNumber;
    private String concertId;
    private BigDecimal amount;
}
