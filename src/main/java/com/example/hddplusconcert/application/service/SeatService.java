package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.in.SeatUseCase;
import com.example.hddplusconcert.application.port.out.SeatRepository;
import com.example.hddplusconcert.common.exception.CustomException;
import com.example.hddplusconcert.common.exception.ErrorCode;
import com.example.hddplusconcert.domain.model.Seat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatService implements SeatUseCase {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    // 좌석을 임시로 예약
    @Override
    public boolean holdSeat(String userId, Long seatNumber, Long concertId) {
        Seat seat = seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)
                .orElseThrow(() -> new CustomException(ErrorCode.SEAT_NOT_FOUND));
        if (seat.isAvailable()) {
            seat.hold(userId, LocalDateTime.now().plusMinutes(5));
            seatRepository.save(seat);
            return true;
        } else {
            throw new CustomException(ErrorCode.SEAT_NOT_AVAILABLE);
        }
    }

    // 예약 가능한 좌석 목록 반환
    @Override
    public List<Seat> getAvailableSeatsForConcert(Long concertId, LocalDateTime date) {
        return List.of();
    }

    @Override
    public void releaseExpiredHolds(Long seatNumber, Long concertId) {
        Seat seat = seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)
                .orElseThrow(() -> new CustomException(ErrorCode.SEAT_NOT_FOUND));
        ;
        seat.releaseHold();
    }

    @Override
    public List<LocalDateTime> getAvailableDatesForConcert(Long concertId) {
        return List.of();
    }

    @Override
    public List<Seat> getSeatsReservedByUser(String userId) {
        return List.of();
    }

    @Override
    public Seat reserveSeat(String userId, Long seatNumber, Long concertId) {
        Seat seat = seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)
                .orElseThrow(() -> new CustomException(ErrorCode.SEAT_NOT_FOUND));

        if (!seat.isAvailable()) {
            throw new CustomException(ErrorCode.SEAT_NOT_AVAILABLE);
        }

        seat.hold(userId, LocalDateTime.now().plusMinutes(5));

        seatRepository.save(seat);
        return seat;
    }
}
