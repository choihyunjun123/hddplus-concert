package com.example.hddplusconcert.application.port.in;

import com.example.hddplusconcert.domain.model.Concert;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertUseCase {
    void registerConcert(Concert concert);
    List<Concert> getAllConcerts();
    List<Concert> getConcertsAvailable(String concertName, LocalDateTime concertDate);
    Concert getConcertByConcertNameAndDate(String concertName, LocalDateTime concertDate);
}
