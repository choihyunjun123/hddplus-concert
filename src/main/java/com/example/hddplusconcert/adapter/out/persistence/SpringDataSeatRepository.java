package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.domain.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SpringDataSeatRepository extends JpaRepository<SeatEntity, SeatId> {
    Optional<SeatEntity> findBySeatNumberAndConcertId(Long seatNumber, Long concertId);

    List<SeatEntity> findAllByStatusAndHeldUntilBefore(Seat.SeatStatus status, LocalDateTime time);
}
