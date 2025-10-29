package com.example.crewstation.common.exception;

public class GuestNotFoundException extends RuntimeException{
    public GuestNotFoundException() {;}
    public GuestNotFoundException(String message) {
        super(message);
    }
}
