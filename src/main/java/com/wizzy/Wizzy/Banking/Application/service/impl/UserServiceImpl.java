package com.wizzy.Wizzy.Banking.Application.service.impl;


import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import com.wizzy.Wizzy.Banking.Application.payload.request.*;
import com.wizzy.Wizzy.Banking.Application.payload.response.AccountInfo;
import com.wizzy.Wizzy.Banking.Application.payload.response.BankResponse;
import com.wizzy.Wizzy.Banking.Application.repository.UserRepository;
import com.wizzy.Wizzy.Banking.Application.service.EmailService;
import com.wizzy.Wizzy.Banking.Application.service.OtpService;
import com.wizzy.Wizzy.Banking.Application.service.TransactionService;
import com.wizzy.Wizzy.Banking.Application.service.UserService;
import com.wizzy.Wizzy.Banking.Application.util.AccountUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final EmailService emailService;

    private  final TransactionService transactionService;

    private final OtpService otpService;

    @Override
    public BankResponse creditAccount(CreditAndDebitRequest request) {


        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());

        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_NOT_FOUND_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NOT_FOUND_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        UserEntity userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());


        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));

        userRepository.save(userToCredit);

        // Credit Alert

        EmailDetails creditAlert = EmailDetails.builder()
                .subject("CREDIT ALERT")
                .recipient(userToCredit.getEmail())
                .messageBody("Your account has been credited with " + request.getAmount() +
                        " from " + userToCredit.getFirstname() + " " + userToCredit.getOtherName() + " " + userToCredit.getLastName() + " " +
                        "your current account balance is " +
                        userToCredit.getAccountBalance())
                .build();
        emailService.sendEmailAlert(creditAlert);

        //Implement Transaction for statement of account

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountNumber(userToCredit.getAccountNumber())
                .transactionType("CREDIT")
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(transactionRequest);

        return BankResponse.builder()
                .responseCode(AccountUtil.ACCOUNT_CREDITED_SUCCESS_CODE)
                .responseMessage(AccountUtil.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstname() + " " + userToCredit.getOtherName() + " " + userToCredit.getLastName())
                        .accountNumber(request.getAccountNumber())
                        .accountBalance(userToCredit.getAccountBalance())
                        .build())
                .build();


}


    @Override
    public BankResponse debitAccount(CreditAndDebitRequest request) {

        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());

        if (!isAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_NOT_FOUND_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NOT_FOUND_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        UserEntity userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());


        // check for insufficient account balance

        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();

        if (availableBalance.intValue() < debitAmount.intValue()) {
            return BankResponse.builder()
                    .responseCode(AccountUtil.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtil.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        } else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
            userRepository.save(userToDebit);

            // Debit Alert

            EmailDetails debitAlert = EmailDetails.builder()
                    .subject("DEBIT ALERT")
                    .recipient(userToDebit.getEmail())
                    .messageBody("The sum of " + request.getAmount() +
                            " has been deducted from your account! Your current account balance is " +
                            userToDebit.getAccountBalance())
                    .build();

            emailService.sendEmailAlert(debitAlert);

            // Implement Transaction service

            TransactionRequest transactionRequest = TransactionRequest.builder()
                    .accountNumber(userToDebit.getAccountNumber())
                    .transactionType("DEBIT")
                    .amount(request.getAmount())
                    .build();

            transactionService.saveTransaction(transactionRequest);



            return BankResponse.builder()
                    .responseCode(AccountUtil.INSUFFICIENT_DEBITED_SUCCESS_CODE)
                    .responseMessage(AccountUtil.INSUFFICIENT_DEBITED_SUCCESS_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(userToDebit.getFirstname() + " " + userToDebit.getLastName())
                            .accountBalance(userToDebit.getAccountBalance())
                            .accountNumber(request.getAccountNumber())
                            .build())
                    .build();
        }
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {

        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());

        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_FOUND_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_NOT_FOUND_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        UserEntity foundUser = userRepository.findByAccountNumber(request.getAccountNumber());


        return BankResponse.builder()
                .responseCode(AccountUtil.ACCOUNT_NUMBER_FOUND_CODE)
                .responseMessage(AccountUtil.ACCOUNT_NUMBER_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .accountName(foundUser.getFirstname() + " " + foundUser.getLastName())
                        .build())
                .build();

    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {

        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());

        if (!isAccountExists) {
            return AccountUtil.ACCOUNT_NUMBER_NOT_FOUND_MESSAGE;
        }


        UserEntity foundUser = userRepository.findByAccountNumber(request.getAccountNumber());

        return foundUser.getFirstname() + " " + foundUser.getLastName();
    }

    @Override
    public BankResponse transfer(TransferRequest request) {

        boolean isDestinationAccountExists = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());

        if (!isDestinationAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtil.ACCOUNT_NUMBER_FOUND_CODE)
                    .responseMessage(AccountUtil.ACCOUNT_NUMBER_FOUND_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        UserEntity sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());

        if (request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtil.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtil.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

// Implementation of OTP

        BigDecimal threshHoldAmount = new BigDecimal("500000");

        if (request.getAmount().compareTo(threshHoldAmount) >= 0){
            String otp = otpService.generateOtp(sourceAccountUser.getEmail());
            EmailDetails otpEmail = EmailDetails.builder()
                    .subject("OTP for transfer verification")
                    .recipient(sourceAccountUser.getEmail())
                    .messageBody("Your OTP for the transfer of " + request.getAmount() + " is: " + otp)
                    .build();

            emailService.sendEmailAlert(otpEmail);

            return BankResponse.builder()
                    .responseCode("009")
                    .responseMessage("OTP IS REQUIRED: " + otp)
                    .accountInfo(null)
                    .build();
        }

        return completeTransfer(sourceAccountUser, request);
    }

    @Override
    public BankResponse verifyOtpAndCompleteTransfer(TransferRequest request, String otp) {

        UserEntity sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());

        boolean isOtpValid = otpService.validateOpt(sourceAccountUser.getEmail(), otp);

        if (!isOtpValid){
            return BankResponse.builder()
                    .responseCode("011")
                    .responseMessage("Invalid OTP")
                    .accountInfo(null)
                    .build();
        }

        return completeTransfer(sourceAccountUser, request);
    }

    private BankResponse completeTransfer(UserEntity sourceAccountUser, TransferRequest request) {
        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(sourceAccountUser);

        String sourceUserName = sourceAccountUser.getLastName() + " " + sourceAccountUser.getOtherName() + " " + sourceAccountUser.getLastName();

        EmailDetails debitAlert = EmailDetails.builder()
                .subject("DEBIT ALERT")
                .recipient(sourceAccountUser.getEmail())
                .messageBody("The sum of " + request.getAmount() +
                        "has been deducted from your account! Your current account balance is " +
                        sourceAccountUser.getAccountBalance())
                .build();

        emailService.sendEmailAlert(debitAlert);

        TransactionRequest debitTransactionRequest = TransactionRequest.builder()
                .accountNumber(sourceAccountUser.getAccountNumber())
                .transactionType("DEBIT")
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(debitTransactionRequest);

        UserEntity destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
        userRepository.save(destinationAccountUser);

        EmailDetails creditAlert = EmailDetails.builder()
                .subject("CREDIT ALERT")
                .recipient(destinationAccountUser.getEmail())
                .messageBody("Your account has been credited with " +
                        request.getAmount() + " from " + sourceUserName +
                        " your current account balance is " + destinationAccountUser.getAccountBalance())
                .build();

        emailService.sendEmailAlert(creditAlert);

        TransactionRequest creditTransactionRequest = TransactionRequest.builder()
                .accountNumber(destinationAccountUser.getAccountNumber())
                .transactionType("Credit")
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(creditTransactionRequest);

        //Invalidate or Clear Otp

        otpService.clearOtp(sourceAccountUser.getEmail());

        return BankResponse.builder()
                .responseCode(AccountUtil.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtil.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
                .build();
    }
}