package com.example.hddplusconcert.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BalanceRequest {
    private String userId;
    private BigDecimal amount;
}
