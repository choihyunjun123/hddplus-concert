package com.example.hddplusconcert.common.dto.concert;

import com.example.hddplusconcert.domain.model.Concert;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public record ConcertRegisterRequest(
        String concertName,
        String location,
        Integer availableSeats,
        LocalDateTime concertDate
) {
    public ConcertRegisterRequest {
        if (availableSeats == null) {
            availableSeats = 50;
        }
    }

    public Concert toDomainModel() {
        Concert concert = new Concert();
        BeanUtils.copyProperties(this, concert);
        return concert;
    }
}