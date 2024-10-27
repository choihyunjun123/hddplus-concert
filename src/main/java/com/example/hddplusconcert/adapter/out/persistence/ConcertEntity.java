package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.domain.model.Concert;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "concerts")
@Getter
@Setter
@NoArgsConstructor
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concertId;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String concertName;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime concertDate;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String location;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer availableSeats;

    public Concert toDomainModel() {
        Concert concert = new Concert();
        BeanUtils.copyProperties(this, concert);
        return concert;
    }

    public static ConcertEntity fromDomainModel(Concert concert) {
        ConcertEntity entity = new ConcertEntity();
        BeanUtils.copyProperties(concert, entity);
        return entity;
    }
}
