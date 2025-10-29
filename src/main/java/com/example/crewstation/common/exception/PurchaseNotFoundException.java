package com.example.crewstation.common.exception;

public class PurchaseNotFoundException extends RuntimeException {
    public PurchaseNotFoundException() {}
    public PurchaseNotFoundException(String message) {
        super(message);
    }
}
