package com.example.scheduleproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {

    private Integer schedileId; //일정ID
    private String userName; //유저명
    private String content; //내용
    private String userPwd; //유저PWD

}
