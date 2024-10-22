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
    public Optional<User> findByUserId(String userId) {
        return repository.findByUserId(userId)
                .map(UserEntity::toDomainModel);
    }

    @Override
    public User save(User user) {
        UserEntity savedEntity = repository.save(UserEntity.fromDomainModel(user));
        return savedEntity.toDomainModel();
    }

    @Override
    public List<User> findAll() {
        return repository.findAll()
                .stream().map(UserEntity::toDomainModel)
                .collect(Collectors.toList());
    }
}
