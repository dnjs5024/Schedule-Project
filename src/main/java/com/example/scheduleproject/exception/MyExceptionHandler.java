package com.example.scheduleproject.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(CustomException.class)//비밀번호가 틀렸을 경우 오류 클라이언트로
    public ResponseEntity<String> customExceptionHandler(CustomException customException){
        return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IndexOutOfBoundsException.class)//db조회결과가 없을 떄 오류 클라이언트로
    public ResponseEntity<String> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException customException){
        return new ResponseEntity<>("db에 없어요", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //Controller의 @Valid에 걸렸을 때 오류 클라이언트로 보내주는 메소드
    public ResponseEntity<String> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException me){
        return new ResponseEntity<>(me.getBindingResult().getFieldErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
