package com.example.scheduleproject.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {

    private String userName; //유저명
    private int userId; //유저ID
    @Size(max = 10 ,message = "10글자 이하로 작성해주세요")
    private String content; //내용
    private String userPwd; //유저PWD

}
