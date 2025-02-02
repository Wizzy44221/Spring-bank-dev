package com.wizzy.Wizzy.Banking.Application.service;

import com.wizzy.Wizzy.Banking.Application.payload.request.TransactionRequest;

public interface TransactionService {

    void saveTransaction(TransactionRequest transactionRequest);
}
