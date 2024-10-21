package com.example.hddplusconcert.common.dto.concert;

import com.example.hddplusconcert.domain.model.Concert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertResponse {
    private Long concertId;
    private String concertName;
    private LocalDateTime concertDate;
    private String location;
    private int availableSeats;

    public static ConcertResponse fromDomainModel(Concert concert) {
        ConcertResponse response = new ConcertResponse();
        BeanUtils.copyProperties(concert, response);
        return response;
    }
}
