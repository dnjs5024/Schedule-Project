package com.example.scheduleproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UsersRequestDto {
    
    private int UserId;

    @NotBlank(message = "이름을 입력해 주세요")
    private String userName;

    @NotBlank(message = "이메일을 입력해 주세요")
    @Email(message = "이메일 형식이 올바르지 않아요")
    private String userEmail;
    
    private String createdAt;

    private String updatedAt;

}
