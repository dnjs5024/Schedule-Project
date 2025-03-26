package com.example.scheduleproject.entity.repository;

import com.example.scheduleproject.dto.UsersResponseDto;
import com.example.scheduleproject.entity.Users;
import org.springframework.http.ResponseEntity;

public interface UserRepository {

    public ResponseEntity<UsersResponseDto> userSignUp(Users users);//유저 정보 users 테이블에 저장

    public String getCurrentTime();//현재 시간 가져옴
}
