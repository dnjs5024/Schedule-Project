package com.example.scheduleproject.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> test(CustomException customException){
        return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<String> test(IndexOutOfBoundsException customException){
        return new ResponseEntity<>("db에 없어요", HttpStatus.BAD_REQUEST);
    }

}
