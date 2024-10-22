package com.example.hddplusconcert.application.port.out;

import com.example.hddplusconcert.domain.model.Seat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatRepository {
    // 좌석 번호와 콘서트 ID로 좌석을 조회하는 메서드 (동시성 제어를 위해 락 사용)
    Optional<Seat> findBySeatNumberAndConcertId(Long seatNumber, Long concertId);

    // 좌석 저장
    void save(Seat seat);

    // 여러 좌석 저장
    void saveAll(List<Seat> seats);

    // 임시저장 시간 만료 목록
    List<Seat> findAllByStatusAndHeldUntilBefore(Seat.SeatStatus status, LocalDateTime date);
}
