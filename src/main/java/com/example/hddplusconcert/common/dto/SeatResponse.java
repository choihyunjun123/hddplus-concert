package com.example.hddplusconcert.common.dto;

import com.example.hddplusconcert.domain.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeatResponse {
    private Long seatNumber;
    private Seat.SeatStatus seatStatus;
}
