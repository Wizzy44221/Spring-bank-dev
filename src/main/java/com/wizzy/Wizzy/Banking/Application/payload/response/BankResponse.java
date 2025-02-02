package com.wizzy.Wizzy.Banking.Application.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class BankResponse {

    private String responseCode;

    private  String responseMessage;

    private AccountInfo accountInfo;

    public BankResponse(String Message) {
        this.responseMessage = Message;
    }

    public BankResponse(String responseCode, String responseMessage, AccountInfo accountInfo) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.accountInfo = accountInfo;
    }
}
