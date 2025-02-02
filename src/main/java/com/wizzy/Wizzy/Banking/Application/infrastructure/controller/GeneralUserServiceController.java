package com.wizzy.Wizzy.Banking.Application.infrastructure.controller;


import com.wizzy.Wizzy.Banking.Application.service.GeneralService;
import com.wizzy.Wizzy.Banking.Application.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class GeneralUserServiceController {
    private final GeneralService generalService;


    @PutMapping("/profile-picture")
    public ResponseEntity<String> uploadProfilePics(@RequestParam MultipartFile profilePic){
        if (profilePic.getSize() > AppConstants.MAX_FILE_SIZE){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("File size exceed the normal limit");
        }
        return generalService.uploadProfilePics(profilePic);
    }
}
