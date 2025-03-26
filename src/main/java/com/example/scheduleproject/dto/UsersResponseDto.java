package com.example.scheduleproject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsersResponseDto {

    private int UserId;

    private String userName;

    private String userEmail;
    
    private String createdAt;

    private String updatedAt;


}
