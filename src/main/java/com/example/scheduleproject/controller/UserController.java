package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.UsersRequestDto;
import com.example.scheduleproject.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {//users table 컨트롤러

    private final ScheduleService scheduleService;

    public UserController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/v1/user")//유저 정보 users 테이블에 저장
    public ResponseEntity<UsersRequestDto> userSignUpController(@RequestBody UsersRequestDto usersRequestDto) {
        return scheduleService.userSignUp(usersRequestDto);
    }

}
