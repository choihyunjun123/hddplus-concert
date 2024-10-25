package com.example.hddplusconcert.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Concert {
    private Long concertId;
    private String concertName;
    private LocalDateTime concertDate;
    private String location;
    private Integer availableSeats;
}
