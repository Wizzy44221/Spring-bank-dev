package com.wizzy.Wizzy.Banking.Application.service.impl;


import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import com.wizzy.Wizzy.Banking.Application.infrastructure.security.JwtAuthenticationFilter;
import com.wizzy.Wizzy.Banking.Application.infrastructure.security.JwtTokenProvider;
import com.wizzy.Wizzy.Banking.Application.repository.UserRepository;
import com.wizzy.Wizzy.Banking.Application.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeneralUserServiceImpl implements GeneralService {

    private final FileUploadImpl fileUpload;

    private  final UserRepository userRepository;

    private  final JwtAuthenticationFilter authenticationFilter;

    private final HttpServletRequest request;

    private final JwtTokenProvider jwtTokenProvider;




    @Override
    public ResponseEntity<String> uploadProfilePics(MultipartFile profilePics) {

        String token = authenticationFilter.getTokenFromRequest(request);
        String email = jwtTokenProvider.getUsername(token);

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

        String fileUrl = null;

        try {
            if (userEntityOptional.isPresent()){
                fileUrl = fileUpload.uploadFile(profilePics);

                UserEntity userEntity = userEntityOptional.get();
                userEntity.setProfilePicture(fileUrl);

                userRepository.save(userEntity);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Uploaded Successfully");
    }
}
