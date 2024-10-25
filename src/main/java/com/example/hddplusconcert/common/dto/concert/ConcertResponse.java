package com.example.hddplusconcert.common.dto.concert;

import com.example.hddplusconcert.domain.model.Concert;

import java.time.LocalDateTime;

public record ConcertResponse(
        Long concertId,
        String concertName,
        LocalDateTime concertDate,
        String location,
        Integer availableSeats
) {


    public static ConcertResponse fromDomainModel(Concert concert) {
        return new ConcertResponse(
                concert.getConcertId(),
                concert.getConcertName(),
                concert.getConcertDate(),
                concert.getLocation(),
                concert.getAvailableSeats()
        );
    }
}
