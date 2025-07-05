package com.jalp.repository;

import com.jalp.entity.UserEntity;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByMobileNumber(String mobileNumber);
}