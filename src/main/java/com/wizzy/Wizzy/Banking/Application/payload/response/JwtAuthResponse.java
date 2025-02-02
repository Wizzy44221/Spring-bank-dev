package com.wizzy.Wizzy.Banking.Application.payload.response;


import com.wizzy.Wizzy.Banking.Application.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthResponse {

    private Long id;

    private  String firstName;

    private  String lastName;

    private  String email;

    private String gender;

    private Role role;

    private  String occupation;

    private String address;

    private String stateOfOrigin;

    private  String status;

    private String phoneNumber;


    private  String profilePicture;

    private  String accessToken;

    private String tokenType = "Bearer";
}
