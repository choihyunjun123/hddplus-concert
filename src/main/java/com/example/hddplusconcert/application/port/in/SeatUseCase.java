package com.example.hddplusconcert.application.port.in;

import com.example.hddplusconcert.domain.model.Seat;

public interface SeatUseCase {
    // 좌석을 임시로 예약
    Long holdSeat(String userId, Long seatNumber, Long concertId);

    Seat reserveSeat(String userId, Long seatNumber, Long concertId);
}
