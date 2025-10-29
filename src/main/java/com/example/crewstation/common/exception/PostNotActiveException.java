package com.example.crewstation.common.exception;

public class PostNotActiveException extends RuntimeException {
    public PostNotActiveException() {};
    public PostNotActiveException(String message) {
        super(message);
    }
}
