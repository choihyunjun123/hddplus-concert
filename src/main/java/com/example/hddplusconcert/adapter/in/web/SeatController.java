package com.example.hddplusconcert.adapter.in.web;

import com.example.hddplusconcert.application.port.in.AuthUseCase;
import com.example.hddplusconcert.application.port.in.SeatUseCase;
import com.example.hddplusconcert.common.dto.SeatRequest;
import com.example.hddplusconcert.common.dto.SeatResponse;
import com.example.hddplusconcert.domain.model.Seat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatUseCase seatUseCase;

    public SeatController(SeatUseCase seatUseCase, AuthUseCase authUseCase) {
        this.seatUseCase = seatUseCase;
    }

    // 좌석을 임시로 예약
    @PostMapping("/hold")
    public ResponseEntity<?> holdSeat(
            @RequestHeader("userId") String userId,
            @RequestBody SeatRequest seatRequest
    ) {
        Long position = seatUseCase.holdSeat(
                userId,
                seatRequest.seatNumber(),
                seatRequest.concertId()
        );

        if (position == 1L) {
            return ResponseEntity.ok("Seat is held for 5 minutes.");
        }
        return ResponseEntity.ok("Your position is " + position);
    }

    // 좌석 예약
    @PostMapping(value = "/reserve")
    public ResponseEntity<SeatResponse> reserveSeat(
            @RequestHeader("userId") String userId,
            @RequestBody SeatRequest seatRequest
    ) {
        Seat seat = seatUseCase.reserveSeat(
                userId,
                seatRequest.seatNumber(),
                seatRequest.concertId()
                );
        SeatResponse response = SeatResponse.fromDomain(seat);
        return ResponseEntity.ok(response);
    }
}
