package com.wizzy.Wizzy.Banking.Application.service;

import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import com.wizzy.Wizzy.Banking.Application.payload.request.LoginRequest;
import com.wizzy.Wizzy.Banking.Application.payload.request.UserRequest;
import com.wizzy.Wizzy.Banking.Application.payload.response.APIResponse;
import com.wizzy.Wizzy.Banking.Application.payload.response.BankResponse;
import com.wizzy.Wizzy.Banking.Application.payload.response.JwtAuthResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface AuthService {

    BankResponse registerUser(UserRequest userRequest);

    ResponseEntity<APIResponse<JwtAuthResponse>> login(LoginRequest loginRequest);

    void  changePassword(UserEntity theUser, String newPassword);

    String validatePasswordResetToken(String token);

    UserEntity findUserByPasswordToken(String token);

    void createPasswordResetTokenForUser(UserEntity userEntity, String token);

    boolean oldPasswordIsValid(UserEntity userEntity, String oldPassword);

    Optional<UserEntity> findByEmail(String email);
}
