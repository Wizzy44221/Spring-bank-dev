package com.wizzy.Wizzy.Banking.Application.payload.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForgetPasswordRequest {


    private  String newPassword;


    private  String email;


    private String OldPassword;
}
