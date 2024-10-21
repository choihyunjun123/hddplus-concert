package com.example.hddplusconcert.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SpringDataConcertRepository extends JpaRepository<ConcertEntity, Long> {
    List<ConcertEntity> findByConcertName(String concertName);
    Optional<ConcertEntity> findByConcertNameAndConcertDate(String concertName, LocalDateTime concertDate);
    List<ConcertEntity> findByConcertNameAndConcertDateGreaterThanEqualAndAvailableSeatsAfter(String concertName, LocalDateTime concertDate, int availableSeats);
}
