package com.example.hddplusconcert.adapter.in.web;

import com.example.hddplusconcert.application.port.in.AuthUseCase;
import com.example.hddplusconcert.application.port.in.SeatUseCase;
import com.example.hddplusconcert.common.dto.SeatRequest;
import com.example.hddplusconcert.common.dto.SeatResponse;
import com.example.hddplusconcert.domain.model.Seat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatUseCase seatUseCase;
    private final AuthUseCase authUseCase;

    public SeatController(SeatUseCase seatUseCase, AuthUseCase authUseCase) {
        this.seatUseCase = seatUseCase;
        this.authUseCase = authUseCase;
    }

    // 콘서트별로 특정 날짜의 예약 가능한 좌석 목록 조회
    @GetMapping("/concert/{concertId}/available")
    public ResponseEntity<List<SeatResponse>> getAvailableSeatsForConcert(@PathVariable Long concertId, @RequestParam String date) {
        List<Seat> seats = seatUseCase.getAvailableSeatsForConcert(concertId, LocalDateTime.parse(date));
        List<SeatResponse> responses = seats.stream()
                .map(seat -> new SeatResponse(seat.getSeatNumber(), seat.getStatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // 좌석을 임시로 예약
    @PostMapping("/hold")
    public ResponseEntity<?> holdSeat(@RequestBody SeatRequest seatRequest) {
        seatUseCase.holdSeat(seatRequest.getUserId(), seatRequest.getSeatNumber(), seatRequest.getConcertId());
        return ResponseEntity.ok("Seat held successfully");
    }

    // 좌석 예약
    @PostMapping("/reserve")
    public ResponseEntity<Seat> reserveSeat(
            @RequestHeader("Authorization") String token,
            @RequestBody SeatRequest seatRequest) {
        // Bearer 부분을 제거하여 순수한 토큰만 사용
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);  // "Bearer " 이후의 실제 토큰 부분 추출
        }
        // 대기열 순서와 토큰 검증
        authUseCase.validateAccessRights(token, seatRequest.getUserId());

        Seat seat = seatUseCase.reserveSeat(
                seatRequest.getUserId(),
                seatRequest.getSeatNumber(),
                seatRequest.getConcertId()
        );

//        if (success) {
//            return ResponseEntity.ok("Seat has been reserved");
//        } else {
//            return ResponseEntity.status(400).body("Seat has not been reserved");
//        }
        return ResponseEntity.ok(seat);
    }

    // 유저가 예약한 좌석 목록 조회
//    @GetMapping("/my-reservations")
//    public ResponseEntity<List<SeatResponse>> getMyReservedSeats(@RequestParam String userId) {
//        List<Seat> seats = seatUseCase.getSeatsReservedByUser(userId);
//        List<SeatResponse> responses = seats.stream()
//                .map(seat -> new SeatResponse(seat.getSeatNumber(), seat.getStatus()))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responses);
//    }
}
