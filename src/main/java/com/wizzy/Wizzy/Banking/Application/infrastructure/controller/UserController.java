package com.wizzy.Wizzy.Banking.Application.infrastructure.controller;


import com.wizzy.Wizzy.Banking.Application.payload.request.CreditAndDebitRequest;
import com.wizzy.Wizzy.Banking.Application.payload.request.EnquiryRequest;
import com.wizzy.Wizzy.Banking.Application.payload.request.TransferRequest;
import com.wizzy.Wizzy.Banking.Application.payload.response.BankResponse;
import com.wizzy.Wizzy.Banking.Application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Account Management APIs")

public class UserController {

    private final UserService userService;


    @Operation(
            summary = "Credit Account Action",
            description = "This Api is used for crediting account"
    )

    @ApiResponse(
            responseCode = "005",
            description = "Account credited successfully!"
    )

    @PostMapping("/credit")

    public BankResponse creditAccount(@RequestBody CreditAndDebitRequest request){

        return userService.creditAccount(request);
    }


    @Operation(
            summary = "Debit Account Action",
            description = "This Api is used for debiting account"
    )

    @ApiResponse(
            responseCode = "008",
            description = "Account has been debited successfully!"
    )


    @PostMapping("/debit")
    public BankResponse debitAccount(@RequestBody CreditAndDebitRequest request){

        return  userService.debitAccount(request);
    }


    @Operation(
            summary = "Account Balance Action",
            description = "This Api is used for checking account balance"
    )

    @ApiResponse(
            responseCode = "004",
            description = "Account number found!"
    )


    @GetMapping("/balance")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request){
        return userService.balanceEnquiry(request);
    }


    @Operation(
            summary = "Name Inquiry Action",
            description = "This Api is used for checking account name"
    )

    @ApiResponse(
            responseCode = "008",
            description = "Name Inquiry found"
    )

    @GetMapping("/name")
    public String nameEnquiry(@RequestBody EnquiryRequest request){

        return userService.nameEnquiry(request);

    }


    @Operation(
            summary = "Transfer Action",
            description = "This Api is used for Transfer"
    )

    @ApiResponse(
            responseCode = "009",
            description = "Transfer successful"
    )


    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest request){
        return userService.transfer(request);
}


    @Operation(
            summary = "Verify OTP",
            description = "This Api is used for OTP Verification"
    )

    @ApiResponse(
            responseCode = "200",
            description = "Successful"
    )


@PostMapping("/verify-otp-and-credit")
    public  BankResponse verifyOtpAndCompleteTransfer(
            @RequestBody TransferRequest request,
            @RequestParam String otp)

{
    return userService.verifyOtpAndCompleteTransfer(request, otp);
}
}

