package com.wizzy.Wizzy.Banking.Application.service;

import jakarta.mail.Multipart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface GeneralService {

    ResponseEntity<String> uploadProfilePics(MultipartFile multipartFile);
}
