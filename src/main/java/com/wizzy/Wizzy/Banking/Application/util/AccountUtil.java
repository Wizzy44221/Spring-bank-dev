package com.wizzy.Wizzy.Banking.Application.util;

import java.time.Year;

public class AccountUtil {

    public static  final  String ACCOUNT_EXISTS_CODE = "001";

    public  static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account created";

    public static  final  String ACCOUNT_CREATION_SUCCESS_CODE = "002";

    public  static  final String ACCOUNT_CREATION_SUCCESS_MESSAGE = "Account has been created successfully";

    public  static  final  String ACCOUNT_NUMBER_EXISTS_CODE = "003";

    public static  final  String ACCOUNT_NUMBER_EXISTS_MESSAGE = "Provide account number exists";

    public  static final  String ACCOUNT_NUMBER_FOUND_CODE = "004";

    public  static  final  String ACCOUNT_NUMBER_FOUND_MESSAGE = "Account number found";

    public  static final  String ACCOUNT_NUMBER_NOT_FOUND_CODE = "006";

    public  static final  String ACCOUNT_NUMBER_NOT_FOUND_MESSAGE = "Account number not found";


    public static final String ACCOUNT_CREDITED_SUCCESS_CODE = "005";

    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Account credited successfully";

    public static final String INSUFFICIENT_BALANCE_CODE = "007";

    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";

    public static final String INSUFFICIENT_DEBITED_SUCCESS_CODE = "008";

    public static final String INSUFFICIENT_DEBITED_SUCCESS_MESSAGE = "Account has been debited successfully";

    public static final String TRANSFER_SUCCESSFUL_CODE = "009";

    public static final String TRANSFER_SUCCESSFUL_MESSAGE = "Transfer Successful";


    public  static String generateAccountNumber(){

        //this will give us the first 4 digits
        Year currentYear = Year.now();

        int min = 100000;
        int max = 999999;

        int randomNumber = (int)Math.floor(Math.random() * (max - min + 1) + min);

        String year = String.valueOf(currentYear);
        String randomNum = String.valueOf(randomNumber);

        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(randomNum).toString();
    }
}
