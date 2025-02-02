package com.wizzy.Wizzy.Banking.Application.infrastructure.exception;

public class EmailNotSendException extends RuntimeException{

    public EmailNotSendException(String message) {
        super(message);
    }
}
