package com.example.demo.exception;

public class BadRequestException extends RuntimeException {

    public String defaultMessage;

    public BadRequestException(String defaultMessage) {
        super(defaultMessage);
        this.defaultMessage = defaultMessage;
    }



}
