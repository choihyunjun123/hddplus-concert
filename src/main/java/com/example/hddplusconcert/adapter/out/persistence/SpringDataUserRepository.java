package com.example.hddplusconcert.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, String> {
}
