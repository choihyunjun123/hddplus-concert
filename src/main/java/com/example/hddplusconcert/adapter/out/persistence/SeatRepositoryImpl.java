package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.application.port.out.SeatRepository;
import com.example.hddplusconcert.domain.model.Seat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class SeatRepositoryImpl implements SeatRepository {

    private final SpringDataSeatRepository repository;

    public SeatRepositoryImpl(SpringDataSeatRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Seat> findBySeatNumberAndConcertId(Long seatNumber, Long concertId) {
        return repository.findBySeatNumberAndConcertId(seatNumber, concertId)
                .map(SeatEntity::toDomainModel);
    }

    @Override
    public void save(Seat seat) {
        SeatEntity entity = SeatEntity.fromDomainModel(seat);
        repository.save(entity);
    }

    @Override
    public void saveAll(List<Seat> seats) {
        List<SeatEntity> seatEntities = seats.stream()
                .map(SeatEntity::fromDomainModel)
                .toList();
        repository.saveAll(seatEntities);
    }

    @Override
    public List<Seat> findAllByStatusAndHeldUntilBefore(Seat.SeatStatus status, LocalDateTime date) {
        return repository.findAllByStatusAndHeldUntilBefore(status, date)
                .stream()
                .map(SeatEntity::toDomainModel)
                .toList();
    }
}
