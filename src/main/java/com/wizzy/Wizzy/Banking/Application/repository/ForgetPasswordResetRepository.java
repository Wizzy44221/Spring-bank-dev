package com.wizzy.Wizzy.Banking.Application.repository;

import com.wizzy.Wizzy.Banking.Application.domain.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgetPasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);
}
