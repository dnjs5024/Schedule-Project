package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.UsersRequestDto;
import com.example.scheduleproject.dto.UsersResponseDto;
import com.example.scheduleproject.service.ScheduleService;
import com.example.scheduleproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {//users table 컨트롤러

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *{
     * "userName": "닉네임",
     * "userEmail": "h@h.com"
     * }
     * @param usersRequestDto 이름,이메일만 필수 입력으로 받음
     * @return 등록된 유저 정보 200 성공
     */
    @PostMapping("/v1/user")//유저 정보 users 테이블에 저장
    public ResponseEntity<UsersResponseDto> userSignUpController(@RequestBody @Valid UsersRequestDto usersRequestDto) {
        return userService.userSignUp(usersRequestDto);
    }

}
