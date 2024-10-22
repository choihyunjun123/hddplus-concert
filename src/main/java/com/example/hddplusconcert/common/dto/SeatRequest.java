package com.example.hddplusconcert.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatRequest {
    private Long concertId;
    private Long seatNumber;
}
