package com.wizzy.Wizzy.Banking.Application.service;

import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;

public interface NotificationService {
    void sendLoginNotificationEmail(UserEntity user, HttpServletRequest request);
}
