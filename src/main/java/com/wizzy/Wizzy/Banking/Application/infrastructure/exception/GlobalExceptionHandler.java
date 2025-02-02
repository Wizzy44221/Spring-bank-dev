package com.wizzy.Wizzy.Banking.Application.infrastructure.exception;


import com.wizzy.Wizzy.Banking.Application.payload.request.ErrorDetails;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailNotSendException.class)
    public ResponseEntity<ErrorDetails> handleEmailNotSendException(final EmailNotSendException ex) {

        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .debugMessage("Email Not Sent")
                .build();

        return ResponseEntity.ok(errorDetails);

    }
}
