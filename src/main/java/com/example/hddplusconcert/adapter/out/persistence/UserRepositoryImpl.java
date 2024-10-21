package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.application.port.out.UserRepository;
import com.example.hddplusconcert.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository repository;

    public UserRepositoryImpl(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findById(String userId) {
        return repository.findById(userId)
                .map(UserEntity::toDomainModel);
    }

    @Override
    public void save(User user) {
        repository.save(UserEntity.fromDomainModel(user));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll()
                .stream().map(UserEntity::toDomainModel)
                .collect(Collectors.toList());
    }
}
