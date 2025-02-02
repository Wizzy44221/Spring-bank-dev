package com.wizzy.Wizzy.Banking.Application.infrastructure.controller;


import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import com.wizzy.Wizzy.Banking.Application.event.listener.RegistrationCompleteEventListener;
import com.wizzy.Wizzy.Banking.Application.payload.request.ForgetPasswordRequest;
import com.wizzy.Wizzy.Banking.Application.payload.request.LoginRequest;
import com.wizzy.Wizzy.Banking.Application.payload.request.UserRequest;
import com.wizzy.Wizzy.Banking.Application.payload.response.APIResponse;
import com.wizzy.Wizzy.Banking.Application.payload.response.BankResponse;
import com.wizzy.Wizzy.Banking.Application.payload.response.JwtAuthResponse;
import com.wizzy.Wizzy.Banking.Application.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

import static com.wizzy.Wizzy.Banking.Application.util.AuthenticationUtils.applicationUrl;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    private final RegistrationCompleteEventListener eventListener;

    @PostMapping("register")
    public BankResponse createAccount(@Valid @RequestBody UserRequest userRequest){

        return  authService.registerUser(userRequest);
    }


    @PostMapping("login")
    public ResponseEntity<APIResponse<JwtAuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("password-reset-request")
    public String resetPasswordRequest(@RequestBody ForgetPasswordRequest passwordRequest, final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        Optional<UserEntity> userEntity = authService.findByEmail(passwordRequest.getEmail());

        String passwordResetUrl = "";

        if (userEntity.isPresent()){
            String passwordResetToken = UUID.randomUUID().toString();
            authService.createPasswordResetTokenForUser(userEntity.get(), passwordResetToken);

            passwordResetUrl = passwordResetEmailLink(userEntity.get(), applicationUrl(request),passwordResetToken);
        }

        return passwordResetUrl;
    }

    private String passwordResetEmailLink(UserEntity userEntity, String applicationUrl,
                                          String passwordResetToken) throws MessagingException, UnsupportedEncodingException {

        String url = applicationUrl+"/api/v1/auth/reset-password?token="+passwordResetToken;
        eventListener.sendPasswordResetVerificationEmail(url, userEntity);
        log.info("Click the link to reset your password : {}", url);

        return url;
    }

    @PostMapping("password-reset")
    public String resetPassword(@RequestBody ForgetPasswordRequest passwordRequest,
        @RequestParam("token") String token){

        String tokenVerification = authService.validatePasswordResetToken(token);

        if (!tokenVerification.equalsIgnoreCase("valid")){
            return "Invalid password reset token";
        }

        Optional<UserEntity> theUser = Optional.ofNullable(authService.findUserByPasswordToken(token));


        if (theUser.isPresent()){
            authService.changePassword(theUser.get(), passwordRequest.getNewPassword());

            return "Password has been reset successfully";
        }

        return "Invalid password reset token";
    }


    @PostMapping("change-password")
    public String changePassword(@RequestBody ForgetPasswordRequest passwordRequest){

        UserEntity userEntity = authService.findByEmail(passwordRequest.getEmail()).get();

        if (!authService.oldPasswordIsValid(userEntity,passwordRequest.getNewPassword())){
            return "Incorrect old password";
        }

        authService.changePassword(userEntity,passwordRequest.getNewPassword());

        return "Password changed successfully";
    }
}
