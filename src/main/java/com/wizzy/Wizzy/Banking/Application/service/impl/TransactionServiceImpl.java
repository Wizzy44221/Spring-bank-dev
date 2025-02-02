package com.wizzy.Wizzy.Banking.Application.service.impl;


import com.wizzy.Wizzy.Banking.Application.domain.entity.Transaction;
import com.wizzy.Wizzy.Banking.Application.payload.request.TransactionRequest;
import com.wizzy.Wizzy.Banking.Application.repository.TransactionRepository;
import com.wizzy.Wizzy.Banking.Application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;


    @Override
    public void saveTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionRequest.getTransactionType())
                .accountNumber(transactionRequest.getAccountNumber())
                .amount(transactionRequest.getAmount())
                .status("SUCCESS")
                .build();

        transactionRepository.save(transaction);

        System.out.println("Transaction saved successfully");

    }
}
