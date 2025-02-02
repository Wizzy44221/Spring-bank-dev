package com.wizzy.Wizzy.Banking.Application.payload.response;


import com.wizzy.Wizzy.Banking.Application.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class APIResponse <T> {

    private String message;

    private T data;

    private String responseTime;

    public APIResponse(String message, T data) {
        this.message = message;
        this.data = data;
        this.responseTime = DateUtils.dateToString(LocalDateTime.now());
    }

    public APIResponse(String message) {
        this.message = message;
        this.responseTime = DateUtils.dateToString(LocalDateTime.now());
    }
}
