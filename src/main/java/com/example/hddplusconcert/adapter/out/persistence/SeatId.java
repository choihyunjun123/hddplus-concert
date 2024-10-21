package com.example.hddplusconcert.adapter.out.persistence;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class SeatId implements Serializable {
    private Long seatNumber;
    private Long concertId;

    public SeatId() {}

    public SeatId(Long seatNumber, Long concertId) {
        this.seatNumber = seatNumber;
        this.concertId = concertId;
    }
}
