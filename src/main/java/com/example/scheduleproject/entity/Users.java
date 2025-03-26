package com.example.scheduleproject.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Users {

    private int UserId;

    private String userName;

    private String userEmail;

    private String createdAt;

    private String updatedAt;

    public Users() {

    }

    public Users(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }
}
