package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.application.port.out.ConcertRepository;
import com.example.hddplusconcert.domain.model.Concert;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ConcertRepositoryImpl implements ConcertRepository {

    private final SpringDataConcertRepository repository;

    public ConcertRepositoryImpl(SpringDataConcertRepository repository) {
        this.repository = repository;
    }

    @Override
    public Concert save(Concert concert) {
        ConcertEntity concertEntity = ConcertEntity.fromDomainModel(concert);
        ConcertEntity savedConcert = repository.save(concertEntity);
        return savedConcert.toDomainModel();
    }

    @Override
    public List<Concert> findAll() {
        return repository.findAll()
                .stream()
                .map(ConcertEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Concert> findByConcertName(String concertName) {
        return repository.findByConcertName(concertName)
                .stream()
                .map(ConcertEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Concert> findByConcertNameAndConcertDate(String concertName, LocalDateTime date) {
        return repository.findByConcertNameAndConcertDate(concertName, date)
                .map(ConcertEntity::toDomainModel);
    }

    @Override
    public List<Concert> findByConcertNameAndConcertDateGreaterThanEqualAndAvailableSeatsAfter(String concertName, LocalDateTime date) {
        return repository.findByConcertNameAndConcertDateGreaterThanEqualAndAvailableSeatsAfter(concertName, date, 0)
                .stream()
                .map(ConcertEntity::toDomainModel)
                .collect(Collectors.toList());
    }
}
