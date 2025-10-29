package com.example.crewstation.common.exception;

public class SmsSendFailException extends RuntimeException {
    public SmsSendFailException() {}
    public SmsSendFailException(String message) {
        super(message);
    }
}
