package com.wizzy.Wizzy.Banking.Application.service;

import com.wizzy.Wizzy.Banking.Application.payload.request.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);

    void sendEmailWithAttachment(EmailDetails emailDetails);

    void sendSimpleEmail(String to, String subject, String message);
}
