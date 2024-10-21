package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.in.ConcertUseCase;
import com.example.hddplusconcert.application.port.out.ConcertRepository;
import com.example.hddplusconcert.application.port.out.SeatRepository;
import com.example.hddplusconcert.common.exception.CustomException;
import com.example.hddplusconcert.common.exception.ErrorCode;
import com.example.hddplusconcert.domain.model.Concert;
import com.example.hddplusconcert.domain.model.Seat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ConcertService implements ConcertUseCase {

    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;

    public ConcertService(ConcertRepository concertRepository, SeatRepository seatRepository) {
        this.concertRepository = concertRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void registerConcert(Concert concert) {
        concertRepository.findByConcertNameAndConcertDate(concert.getConcertName(), concert.getConcertDate())
                .ifPresent(existingConcert -> {
                    throw new CustomException(ErrorCode.CONCERT_ALREADY_EXIST);
                });
        Concert savedConcert = concertRepository.save(concert);
        List<Seat> seats = IntStream.range(0, savedConcert.getAvailableSeats())
                .mapToObj(i -> new Seat(
                        (long) i + 1,
                        savedConcert.getConcertId()
                ))
                .toList();
        seatRepository.saveAll(seats);

    }

    @Override
    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    @Override
    public List<Concert> getConcertsAvailable(String concertName, LocalDateTime concertDate) {
        List<Concert> concerts = concertRepository
                .findByConcertNameAndConcertDateGreaterThanEqualAndAvailableSeatsAfter(concertName, concertDate);

        if (concerts.isEmpty()) {
            throw new CustomException(ErrorCode.CONCERT_NOT_FOUND);
        }

        return concerts;
    }

    @Override
    public Concert getConcertByConcertNameAndDate(String concertName, LocalDateTime concertDate) {
        return concertRepository.findByConcertNameAndConcertDate(concertName, concertDate)
                .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    }
}
