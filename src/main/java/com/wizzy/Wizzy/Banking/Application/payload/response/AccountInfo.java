package com.wizzy.Wizzy.Banking.Application.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfo {

    private String accountName;

    private BigDecimal accountBalance;

    private String accountNumber;

    }


