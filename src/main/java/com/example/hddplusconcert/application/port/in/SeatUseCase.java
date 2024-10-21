package com.example.hddplusconcert.application.port.in;

import com.example.hddplusconcert.domain.model.Seat;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatUseCase {
    // 좌석을 임시로 예약
    boolean holdSeat(String userId, Long seatNumber, Long concertId);

    // 콘서트별로 예약 가능한 좌석 목록 반환
    List<Seat> getAvailableSeatsForConcert(Long concertId, LocalDateTime date);

    // 임시 예약 만료된 좌석의 임시 예약 해제
    void releaseExpiredHolds(Long seatNumber, Long concertId);

    // 콘서트별로 예약 가능한 날짜 목록을 반환
    List<LocalDateTime> getAvailableDatesForConcert(Long concertId);

    // 유저가 예약한 좌석 목록을 반환
    List<Seat> getSeatsReservedByUser(String userId);

    Seat reserveSeat(String userId, Long seatNumber, Long concertId);
}
