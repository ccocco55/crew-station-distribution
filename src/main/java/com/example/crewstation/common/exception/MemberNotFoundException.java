package com.example.crewstation.common.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException() {;}
    public MemberNotFoundException(String message) {
        super(message);
    }
}
