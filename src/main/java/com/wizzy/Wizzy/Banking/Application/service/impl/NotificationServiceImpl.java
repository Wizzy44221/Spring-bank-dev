package com.wizzy.Wizzy.Banking.Application.service.impl;


import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import com.wizzy.Wizzy.Banking.Application.service.EmailService;
import com.wizzy.Wizzy.Banking.Application.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.wizzy.Wizzy.Banking.Application.util.UtilityClass.getCurrentIpAddress;
import static com.wizzy.Wizzy.Banking.Application.util.UtilityClass.getDeviceDetails;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final EmailService emailService;

    @Override
    public void sendLoginNotificationEmail(UserEntity user, HttpServletRequest request) {
        String ipAddress = getCurrentIpAddress(request);
        String device = getDeviceDetails(request);
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ssa EEEE,MM dd, yyy 'GMT'"));

        String emailContent = String.format(
                """
                     Hello %s,
                     
                     Welcome  back!
                     
                     we noticed a new sign-in to your account using %s at %s.
                     
                     If you signed in recently, no need to worry, and you can disregard this message.
                     
                     If that wasn't you or you don't recognize this sign-in, we strongly recommend you change your password as soon as possible and do not hesitate to contact us if you need any further assistance.
                     
                     Why should you contact us? We take security very seriously and we want to make sure every activities on your account is performed by you.
                     
                     The Wizzy Team""",
                user.getFirstname(), device, ipAddress, timeStamp
        );

        try{
            emailService.sendSimpleEmail(user.getEmail(),"New Sign-In to Your Wizzy Account", emailContent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send login notification email.");
        }

    }
}
