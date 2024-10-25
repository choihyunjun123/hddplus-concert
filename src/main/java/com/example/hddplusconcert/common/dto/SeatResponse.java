package com.example.hddplusconcert.common.dto;

import com.example.hddplusconcert.domain.model.Seat;

import java.time.LocalDateTime;

public record SeatResponse(
        Long seatNumber,
        Long concertId,
        Seat.SeatStatus status,
        String heldByUserId,
        LocalDateTime heldUntil,
        String reservedByUserId,
        LocalDateTime reservedDateTime
) {
    public static SeatResponse fromDomain(Seat seat) {
        return new SeatResponse(
                seat.getSeatNumber(),
                seat.getConcertId(),
                seat.getStatus(),
                seat.getHeldByUserId(),
                seat.getHeldUntil(),
                seat.getReservedByUserId(),
                seat.getReservedDateTime()
        );
    }
}
