package com.example.scheduleproject.exception;

public class CustomException extends RuntimeException {
    public CustomException(String errorMsg){
        super(errorMsg);
    }
}
