package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.UsersRequestDto;
import com.example.scheduleproject.dto.UsersResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<UsersResponseDto> userSignUp(UsersRequestDto usersRequestDto);
}
