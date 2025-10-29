package com.example.crewstation.common.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException() {;}
    public PostNotFoundException(String message) {
        super(message);
    }
}
