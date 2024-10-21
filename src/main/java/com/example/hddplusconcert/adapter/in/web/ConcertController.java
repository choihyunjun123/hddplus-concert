package com.example.hddplusconcert.adapter.in.web;

import com.example.hddplusconcert.application.port.in.ConcertUseCase;
import com.example.hddplusconcert.common.dto.concert.ConcertRegisterRequest;
import com.example.hddplusconcert.common.dto.concert.ConcertResponse;
import com.example.hddplusconcert.domain.model.Concert;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    private final ConcertUseCase concertUseCase;

    public ConcertController(ConcertUseCase concertUseCase) {
        this.concertUseCase = concertUseCase;
    }

    // 콘서트 등록
    @PostMapping("/register")
    public ResponseEntity<?> registerConcert(@RequestBody ConcertRegisterRequest request) {
        concertUseCase.registerConcert(request.toDomainModel());
        return ResponseEntity.ok("Concert registered successfully.");
    }

    // 모든 콘서트 목록의 조회
    @GetMapping
    public ResponseEntity<List<ConcertResponse>> getAllConcert(@RequestParam(required = false) LocalDateTime concertDate) {
        List<Concert> concerts = concertUseCase.getAllConcerts();
        List<ConcertResponse> responses = concerts.stream()
                .map(ConcertResponse::fromDomainModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // 콘서트의 예약 가능한 날짜 조회
    @GetMapping("/available")
    public ResponseEntity<List<ConcertResponse>> getAvailableDates(
            @RequestParam String concertName,
            @RequestParam(required = false) LocalDateTime concertDate) {
        LocalDateTime date = concertDate == null ? LocalDateTime.now() : concertDate;

        List<ConcertResponse> responses = concertUseCase.getConcertsAvailable(concertName, date)
                .stream()
                .map(ConcertResponse::fromDomainModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // 특정 콘서트 조회
    @GetMapping("/find")
    public ResponseEntity<ConcertResponse> getConcert(
            @RequestParam String concertName,
            @RequestParam LocalDateTime concertDate) {
        Concert concert = concertUseCase.getConcertByConcertNameAndDate(concertName, concertDate);
        ConcertResponse responses = ConcertResponse.fromDomainModel(concert);
        return ResponseEntity.ok(responses);
    }
}
