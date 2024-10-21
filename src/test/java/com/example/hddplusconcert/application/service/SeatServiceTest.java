package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.in.SeatUseCase;
import com.example.hddplusconcert.application.port.out.SeatRepository;
import com.example.hddplusconcert.domain.model.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeatServiceTest {

    private SeatUseCase seatUseCase;
    private SeatRepository seatRepository;

    @BeforeEach
    void setUp() {
        seatRepository = mock(SeatRepository.class);
        seatUseCase = new SeatService(seatRepository);
    }

    @Test
    @DisplayName("성공적 좌석 임시 예약")
    void testHoldSeat_Success() {
        String userId = "user123";
        Long seatNumber = 1L;
        Long concertId = 1L;
        LocalDateTime date = LocalDateTime.now();

        Seat seat = new Seat();
        seat.setSeatNumber(seatNumber);
        seat.setConcertId(concertId);
        seat.setStatus(Seat.SeatStatus.AVAILABLE);

        when(seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)).thenReturn(Optional.of(seat));

        boolean result = seatUseCase.holdSeat(userId, seatNumber, concertId);

        assertTrue(result);
        verify(seatRepository).save(seat);
    }

    @Test
    @DisplayName("좌석 임시 예약 실패 - 좌석이 이미 예약된 경우")
    void testHoldSeat_Failure_SeatNotAvailable() {
        String userId = "user123";
        Long seatNumber = 1L;
        Long concertId = 1L;
        LocalDateTime date = LocalDateTime.now();

        Seat seat = new Seat();
        seat.setSeatNumber(seatNumber);
        seat.setConcertId(concertId);
        seat.setStatus(Seat.SeatStatus.RESERVED);

        when(seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)).thenReturn(Optional.of(seat));

        boolean result = seatUseCase.holdSeat(userId, seatNumber, concertId);
        assertFalse(result);
    }

    @Test
    void getAvailableSeatsForConcert() {

    }

    @Test
    void releaseExpiredHolds() {
    }

    @Test
    void getAvailableDatesForConcert() {
    }

    @Test
    void getSeatsReservedByUser() {
    }
}