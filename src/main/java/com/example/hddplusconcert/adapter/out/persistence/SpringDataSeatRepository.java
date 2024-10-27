package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.domain.model.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SpringDataSeatRepository extends JpaRepository<SeatEntity, SeatId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SeatEntity> findBySeatNumberAndConcertId(Long seatNumber, Long concertId);

    List<SeatEntity> findAllByStatusAndHeldUntilBefore(Seat.SeatStatus status, LocalDateTime time);
}
