package com.example.crewstation.common.exception;

public class MessagingException extends RuntimeException {
    public MessagingException() {};
    public MessagingException(String message) {
        super(message);
    }
}
