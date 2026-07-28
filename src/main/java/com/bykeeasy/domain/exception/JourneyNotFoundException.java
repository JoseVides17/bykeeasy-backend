package com.bykeeasy.domain.exception;

public class JourneyNotFoundException extends RuntimeException{
    public JourneyNotFoundException(String message) {
        super(message);
    }
}
