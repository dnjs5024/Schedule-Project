package com.example.scheduleproject.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {

    private String userName; //유저명

    @NotNull(message = "유저ID는 필수 입력 항목 입니다")
    @Min(value = 0, message = "0이상의 값을 넣어주세요")
    private Integer userId; //유저ID

    @Size(max = 10, message = "10글자 이하로 작성해주세요")
    @NotBlank(message = "내용은 필수 입력 항목 입니다")
    private String content; //내용

    @NotBlank(message = "비밀번호는 필수 입력 항목 입니다")
    private String userPwd; //유저PWD

}
