package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.domain.model.Seat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Entity
@Table(name = "seats")
@Getter
@Setter
@NoArgsConstructor
@IdClass(SeatId.class)
public class SeatEntity {

    @Id
    private Long seatNumber;

    @Id
    private Long concertId;

    @Enumerated(EnumType.STRING)
    private Seat.SeatStatus status;

    private String heldByUserId;
    private LocalDateTime heldUntil;
    private String reservedByUserId;
    private LocalDateTime reservedDateTime;

    public Seat toDomainModel() {
        Seat seat = new Seat();
        BeanUtils.copyProperties(this, seat);
        return seat;
    }

    public static SeatEntity fromDomainModel(Seat seat) {
        SeatEntity entity = new SeatEntity();
        BeanUtils.copyProperties(seat, entity);
        return entity;
    }
}
