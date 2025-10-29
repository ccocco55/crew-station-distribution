package com.example.crewstation.common.exception;

public class DiaryNotFoundException extends RuntimeException {
    public DiaryNotFoundException() {}
    public DiaryNotFoundException(String message) {
        super(message);
    }
}
