package com.wizzy.Wizzy.Banking.Application.repository;

import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);


    boolean existsByEmail(String email);

    boolean existsByAccountNumber(String accountNumber);

    UserEntity findByAccountNumber(String accountNumber);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
