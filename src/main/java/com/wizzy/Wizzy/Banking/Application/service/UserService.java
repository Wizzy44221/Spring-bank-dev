package com.wizzy.Wizzy.Banking.Application.service;

import com.wizzy.Wizzy.Banking.Application.payload.request.CreditAndDebitRequest;
import com.wizzy.Wizzy.Banking.Application.payload.request.EnquiryRequest;
import com.wizzy.Wizzy.Banking.Application.payload.request.TransferRequest;
import com.wizzy.Wizzy.Banking.Application.payload.response.BankResponse;

public interface UserService {



    BankResponse creditAccount (CreditAndDebitRequest request);

    BankResponse debitAccount (CreditAndDebitRequest request);

    BankResponse balanceEnquiry(EnquiryRequest request);

    String nameEnquiry(EnquiryRequest request);

    BankResponse transfer(TransferRequest request);

    BankResponse verifyOtpAndCompleteTransfer(TransferRequest request, String otp);
}
