package com.example.crewstation.common.exception;

public class CannotDecreaseBelowZeroException extends RuntimeException {
  public CannotDecreaseBelowZeroException() {}
  public CannotDecreaseBelowZeroException(String message) {
        super(message);
    }
}
