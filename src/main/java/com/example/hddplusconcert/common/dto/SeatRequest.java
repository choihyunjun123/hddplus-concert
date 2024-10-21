package com.example.hddplusconcert.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SeatRequest {
    private String userId;
    private Long concertId;
    private Long seatNumber;
}
