package com.example.hddplusconcert.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    public enum SeatStatus {
        AVAILABLE, HELD, RESERVED
    }

    private Long seatNumber;        // 좌석 번호
    private Long concertId;       // 콘서트 ID
    private SeatStatus status;
    private String heldByUserId;    // 임시 예약된 유저의 ID
    private LocalDateTime heldUntil; // 임시 예약 만료 시간
    private String reservedByUserId; // 최종 예약한 유저의 ID
    private LocalDateTime reservedDateTime; // 최종 예약된 시점

    public Seat(Long seatNumber, Long concertId) {
        this.seatNumber = seatNumber;
        this.concertId = concertId;
        this.status = SeatStatus.AVAILABLE;
        this.heldByUserId = null;
        this.heldUntil = null;
        this.reservedByUserId = null;
        this.reservedDateTime = null;
    }

    // 좌석이 예약 가능한지 확인하는 메서드
    @JsonIgnore
    public boolean isAvailable() {
        return this.status == SeatStatus.AVAILABLE;
    }

    // 좌석을 특정 유저에게 임시로 예약하는 메서드
    public void hold(String userId, LocalDateTime heldUntil) {
        this.status = SeatStatus.HELD;
        this.heldByUserId = userId;
        this.heldUntil = heldUntil;
    }

    // 좌석을 최종 예약 상태로 변경하는 메서드
    public void reserve(String userId) {
        this.status = SeatStatus.RESERVED;
        this.reservedByUserId = userId;
        this.heldByUserId = null;
        this.heldUntil = null;
        this.reservedDateTime = LocalDateTime.now();
    }

    // 좌석이 특정 유저에 의해 임시로 예약되었는지 확인하는 메서드
    public boolean isHeldBy(String userId) {
        return this.heldByUserId != null && this.heldByUserId.equals(userId);
    }

    // 좌석의 임시 예약 시간이 만료되었는지 확인하는 메서드
    @JsonIgnore
    public boolean isHoldExpired() {
        return this.heldUntil != null && LocalDateTime.now().isAfter(this.heldUntil);
    }

    // 좌석의 임시 예약을 해제하고 상태를 다시 AVAILABLE로 변경하는 메서드
    public void releaseHold() {
        this.status = SeatStatus.AVAILABLE;
        this.heldByUserId = null;
        this.heldUntil = null;
    }
}