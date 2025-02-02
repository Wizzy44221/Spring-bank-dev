package com.wizzy.Wizzy.Banking.Application.payload.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String firstName;

    private  String lastName;

    private String email;

    private  String otherName;

    private String gender;

    private String address;

    private String password;

//    private String confirmPassword;

    private  String stateOfOrigin;

    private String occupation;

//    private  String NIN;

    private  String maritalStatus;

    private String dob;

    private String phoneNumber;


}
