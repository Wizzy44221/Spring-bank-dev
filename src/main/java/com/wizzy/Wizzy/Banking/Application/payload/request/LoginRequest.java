package com.wizzy.Wizzy.Banking.Application.payload.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private  String emailOrPhone;

    private String password;
}
