package com.example.hddplusconcert.application.port.out;

import com.example.hddplusconcert.domain.model.Concert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConcertRepository {
    Concert save(Concert concert);
    List<Concert> findAll();
    List<Concert> findByConcertName(String concertName);
    Optional<Concert> findByConcertNameAndConcertDate(String concertName, LocalDateTime date);
    List<Concert> findByConcertNameAndConcertDateGreaterThanEqualAndAvailableSeatsAfter(String concertName, LocalDateTime date);
}
