package com.wizzy.Wizzy.Banking.Application.event;


import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private UserEntity userEntity;

    private  String applicationUrl;

    public RegistrationCompleteEvent(UserEntity userEntity, String applicationUrl) {
        super(userEntity);
        this.userEntity = userEntity;
        this.applicationUrl = applicationUrl;


    }
}
