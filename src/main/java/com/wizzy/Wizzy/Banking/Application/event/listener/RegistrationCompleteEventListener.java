package com.wizzy.Wizzy.Banking.Application.event.listener;


import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import com.wizzy.Wizzy.Banking.Application.event.RegistrationCompleteEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final JavaMailSender mailSender;


    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        event.getUserEntity();

    }

    public void sendPasswordResetVerificationEmail(String url, UserEntity user) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request Verification";
        String senderName = "Wizzy Bank Application";
        String mailContent = "<p> Hi, "+user.getFirstname()+",</p>"+
                "<p><b> You recently requested requested to reset your password,</b>"+""+
                "Please, follow the link below to complete the action.</p>"+
                "<a href=\"" +url+ "\">Reset password</a>"+
                "<p> Wizzy Bank Application";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("wizzypatrick777@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);

        mailSender.send(message);
    }
}
