package com.wizzy.Wizzy.Banking.Application.infrastructure.controller;


import com.itextpdf.text.DocumentException;
import com.wizzy.Wizzy.Banking.Application.domain.entity.Transaction;
import com.wizzy.Wizzy.Banking.Application.service.impl.BankServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statement")
public class TransactionController {

    private final BankServiceImpl bankStatement;


    @GetMapping
    public List<Transaction>generateStatement(@RequestParam String accountNumber,
                                              @RequestParam String startDate,
                                              @RequestParam String endDate) throws DocumentException, FileNotFoundException {
        return bankStatement.generateStatement(accountNumber, startDate, endDate);
    }

}
