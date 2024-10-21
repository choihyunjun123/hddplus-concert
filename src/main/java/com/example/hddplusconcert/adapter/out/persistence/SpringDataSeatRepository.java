package com.example.hddplusconcert.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataSeatRepository extends JpaRepository<SeatEntity, SeatId> {
    Optional<SeatEntity> findBySeatNumberAndConcertId(Long seatNumber, Long concertId);
}
