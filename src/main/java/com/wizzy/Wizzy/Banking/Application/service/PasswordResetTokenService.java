package com.wizzy.Wizzy.Banking.Application.service;

import com.wizzy.Wizzy.Banking.Application.domain.entity.PasswordResetToken;
import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;

import java.util.Optional;

public interface PasswordResetTokenService {

    void  createPasswordResetTokenForUser(UserEntity userEntity, String token);

    String validatePasswordResetToken(String token);

    Optional<UserEntity> findUserByPasswordToken(String token);

    PasswordResetToken findPasswordResetToken(String token);
}
