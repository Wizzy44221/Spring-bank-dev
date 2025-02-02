package com.wizzy.Wizzy.Banking.Application.payload.request;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetails {


    private  String message;

    private HttpStatus status;

    private  String debugMessage;

    private String dateTime;
}
