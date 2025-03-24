package com.example.scheduleproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {

    private String userName; //유저명
    private int userId; //유저ID
    private String content; //내용
    private String userPwd; //유저PWD

}
