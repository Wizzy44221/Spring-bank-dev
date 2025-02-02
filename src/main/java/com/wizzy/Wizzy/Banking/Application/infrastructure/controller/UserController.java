package com.wizzy.Wizzy.Banking.Application.infrastructure.controller;


import com.wizzy.Wizzy.Banking.Application.payload.request.CreditAndDebitRequest;
import com.wizzy.Wizzy.Banking.Application.payload.request.EnquiryRequest;
import com.wizzy.Wizzy.Banking.Application.payload.request.TransferRequest;
import com.wizzy.Wizzy.Banking.Application.payload.response.BankResponse;
import com.wizzy.Wizzy.Banking.Application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/credit")

    public BankResponse creditAccount(@RequestBody CreditAndDebitRequest request){

        return userService.creditAccount(request);
    }

    @PostMapping("/debit")
    public BankResponse debitAccount(@RequestBody CreditAndDebitRequest request){

        return  userService.debitAccount(request);
    }

    @GetMapping("/balance")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request){
        return userService.balanceEnquiry(request);
    }

    @GetMapping("/name")
    public String nameEnquiry(@RequestBody EnquiryRequest request){

        return userService.nameEnquiry(request);
    }

@PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest request){
        return userService.transfer(request);
}

@PostMapping("/verify-otp-and-credit")
    public  BankResponse verifyOtpAndCompleteTransfer(
            @RequestBody TransferRequest request,
            @RequestParam String otp)

{
    return userService.verifyOtpAndCompleteTransfer(request, otp);
}
}

