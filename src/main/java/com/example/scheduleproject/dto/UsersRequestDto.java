package com.example.scheduleproject.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UsersRequestDto {

    private int UserId;

    private String userName;

    @Email(message = "이메일 형식이 올바르지 않아요")
    private String userEmail;
    
    private String createdAt;

    private String updatedAt;

}
