package com.example.scheduleproject.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Users {

    private int UserId;//유저ID

    private String userName;//유저이름

    private String userEmail;//유저이메일

    private String createdAt;//유저등록일

    private String updatedAt;//유저 정보 수정일


    public Users(String userName, String userEmail) {

        this.userName = userName;
        this.userEmail = userEmail;

    }
}
