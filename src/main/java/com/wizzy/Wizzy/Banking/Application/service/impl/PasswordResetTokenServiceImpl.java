package com.wizzy.Wizzy.Banking.Application.service.impl;


import com.wizzy.Wizzy.Banking.Application.domain.entity.PasswordResetToken;
import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import com.wizzy.Wizzy.Banking.Application.repository.ForgetPasswordResetRepository;
import com.wizzy.Wizzy.Banking.Application.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private  final ForgetPasswordResetRepository resetPasswordRepository;

    @Override
    public void createPasswordResetTokenForUser(UserEntity userEntity, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, userEntity);

        resetPasswordRepository.save(passwordResetToken);

    }

    @Override
    public String validatePasswordResetToken(String token) {

        PasswordResetToken passwordResetToken = resetPasswordRepository.findByToken(token);

        if (passwordResetToken == null) {
            return "Invalid verification token";
        }

        UserEntity userEntity = passwordResetToken.getUserEntity();
        Calendar calendar = Calendar.getInstance();

        if ((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return "Link already expired, resend link";
        }

        return "Valid";

    }

    @Override
    public Optional<UserEntity> findUserByPasswordToken(String token) {
        return Optional.ofNullable(resetPasswordRepository.findByToken(token).getUserEntity());
    }

    @Override
    public PasswordResetToken findPasswordResetToken(String token) {
        return resetPasswordRepository.findByToken(token);
    }
}
