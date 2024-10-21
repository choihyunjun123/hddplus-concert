package com.example.hddplusconcert.common.dto.concert;

import com.example.hddplusconcert.domain.model.Concert;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConcertRegisterRequest {
    private String concertName;
    private String location;
    private int availableSeats = 50;
    private LocalDateTime concertDate;

    public Concert toDomainModel() {
        Concert concert = new Concert();
        BeanUtils.copyProperties(this, concert);
        return concert;
    }
}
