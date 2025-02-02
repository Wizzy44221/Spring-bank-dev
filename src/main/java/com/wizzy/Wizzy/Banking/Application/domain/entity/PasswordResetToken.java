package com.wizzy.Wizzy.Banking.Application.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "password_reset_token_tbl")
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetToken extends BaseClass{

    private  static  final int EXPIRATION_TIME = 10;

    private String token;

    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public PasswordResetToken(String token, UserEntity userEntity){
        this.token = token;
        this.userEntity = userEntity;
        this.expirationTime = getTokenExpirationTime();
    }


    public PasswordResetToken(String token){
        this.token = token;
        this.expirationTime = getTokenExpirationTime();
    }


    private Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);

        return new Date(calendar.getTime().getTime());
    }


}
