package com.wizzy.Wizzy.Banking.Application.service.impl;


import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import com.wizzy.Wizzy.Banking.Application.domain.enums.Role;
import com.wizzy.Wizzy.Banking.Application.infrastructure.security.JwtTokenProvider;
import com.wizzy.Wizzy.Banking.Application.payload.request.EmailDetails;
import com.wizzy.Wizzy.Banking.Application.payload.request.LoginRequest;
import com.wizzy.Wizzy.Banking.Application.payload.request.UserRequest;
import com.wizzy.Wizzy.Banking.Application.payload.response.APIResponse;
import com.wizzy.Wizzy.Banking.Application.payload.response.AccountInfo;
import com.wizzy.Wizzy.Banking.Application.payload.response.BankResponse;
import com.wizzy.Wizzy.Banking.Application.payload.response.JwtAuthResponse;
import com.wizzy.Wizzy.Banking.Application.repository.ForgetPasswordResetRepository;
import com.wizzy.Wizzy.Banking.Application.repository.UserRepository;
import com.wizzy.Wizzy.Banking.Application.service.AuthService;
import com.wizzy.Wizzy.Banking.Application.service.EmailService;
import com.wizzy.Wizzy.Banking.Application.service.NotificationService;
import com.wizzy.Wizzy.Banking.Application.util.AccountUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private  final PasswordEncoder passwordEncoder;

    private  final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    private  final JwtTokenProvider jwtTokenProvider ;

    private final PasswordResetTokenServiceImpl passwordResetTokenService;

    private final NotificationService notificationService;

    private final HttpServletRequest request;

    private final ForgetPasswordResetRepository passwordResetRepository;


    @Override
    public BankResponse registerUser(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            BankResponse response =BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();

            return  response;

        }

        UserEntity newUser = UserEntity.builder()
                .firstname(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .dob(userRequest.getDob())
                .password(passwordEncoder.encode(userRequest.getPassword()))
//                .NIN(passwordEncoder.encode(userRequest.getNIN()))
                .address(userRequest.getAddress())
                .occupation(userRequest.getOccupation())
                .phoneNumber(userRequest.getPhoneNumber())
                .maritalStatus(userRequest.getMaritalStatus())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .profilePicture("https://res.cloudinary.com/dpfqbb9pl/image/upload/v1701260428/maleprofile_ffeep9.png%22")
                .status("Active")
                .accountNumber(AccountUtil.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .role(Role.USER)
                .build();


        UserEntity savedUser = userRepository.save(newUser);


        // SEND EMAIL ALERT
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("CONGRATULATIONS! Your Account Has Been Successfully Created.\n Your Account Details: \n Account Name: " + savedUser.getFirstname() + " " + savedUser.getOtherName() + " " + savedUser.getLastName() +
                        "\nAccount Number: " + savedUser.getAccountNumber())
                .build();

        emailService.sendEmailAlert(emailDetails);




            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_CREATION_SUCCESS_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(savedUser.getAccountBalance())
                            .accountNumber(savedUser.getAccountNumber())
                            .accountName(savedUser.getFirstname() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                            .build())
                    .build();
        }

    @Override
    public ResponseEntity<APIResponse<JwtAuthResponse>> login(LoginRequest loginRequest) {

        String emailOrPhone = loginRequest.getEmailOrPhone();

      //  Optional<UserEntity> userEntityOptional = userRepository.findByEmail(loginRequest.getEmail());

        Optional<UserEntity> userEntityOptional = isEmail(emailOrPhone)
                ? userRepository.findByEmail(emailOrPhone)
                : userRepository.findByPhoneNumber(emailOrPhone);

        if (userEntityOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new APIResponse<>("Invalid email / phone number or password", null));

        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(emailOrPhone, loginRequest.getPassword())
        );


//        Authentication authentication = null;
//
//        authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getEmail(),
//                        loginRequest.getPassword()
//                )
//        );
//
//
//        EmailDetails loginAlert = EmailDetails.builder()
//                .subject("You are Logged in!")
//                .recipient((loginRequest.getEmailOrPhone()))
//                .messageBody("You logged into your account. If you did not initiate this request, contact support desk.")
//                .build();

        //emailService.sendEmailAlert(loginAlert);

        UserEntity userEntity = userEntityOptional.get();

        notificationService.sendLoginNotificationEmail(userEntity, request);


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);


        return  ResponseEntity.status(HttpStatus.OK)
                .body(
                        new APIResponse<>(
                                "Login Successful",
                                JwtAuthResponse.builder()
                                        .accessToken(token)
                                        .tokenType("BEARER")
                                        .id(userEntity.getId())
                                        .email(userEntity.getEmail())
                                        .gender(userEntity.getGender())
                                        .firstName(userEntity.getFirstname())
                                        .lastName(userEntity.getLastName())
                                        .profilePicture(userEntity.getProfilePicture())
                                        .role(userEntity.getRole())
                                        .occupation(userEntity.getOccupation())
                                        .address(userEntity.getAddress())
                                        .stateOfOrigin(userEntity.getStateOfOrigin())
                                        .status(userEntity.getStatus())
                                        .phoneNumber(userEntity.getPhoneNumber())
                                        .build()
                        )
                );
    }

    private boolean isEmail(String input) {
        return input.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    @Override
    public void changePassword(UserEntity theUser, String newPassword) {
        theUser.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(theUser);

    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetToken(token);
    }

    @Override
    public UserEntity findUserByPasswordToken(String token) {
        return passwordResetTokenService.findUserByPasswordToken(token).get();
    }

    @Override
    public void createPasswordResetTokenForUser(UserEntity userEntity, String token) {
        passwordResetTokenService.createPasswordResetTokenForUser(userEntity, token);

    }

    @Override
    public boolean oldPasswordIsValid(UserEntity userEntity, String oldPassword) {
        return passwordEncoder.matches(oldPassword,userEntity.getPassword());
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
@Transactional
    @Override
    public void clearPasswordResetToken(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        passwordResetRepository.deleteByUserEntity(user);

    }

}
