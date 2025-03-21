package com.example.scheduleproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private int scheduleId; //일정ID
    private String userName; //유저명
    private String updatedAt; //수정일자
    private String content; //내용

}
