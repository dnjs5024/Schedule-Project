package com.example.scheduleproject.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {

    private int scheduleId; //일정ID

    private int userId; //유저ID

    private String userName; //유저명

    private String createdAt; //생성일자

    private String updatedAt; //수정일자

    private String content; //내용

    private String userPwd; //유저PWD

    public Schedule(String userName, int userId, String userPwd, String content) {

        this.userName = userName;
        this.userId = userId;
        this.userPwd = userPwd;
        this.content = content;

    }

}
