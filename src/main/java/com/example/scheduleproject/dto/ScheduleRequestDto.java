package com.example.scheduleproject.dto;

import com.example.scheduleproject.groups.AddValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {

    private String userName; //유저명

    @Min(value = 1, message = "1 이상의 값을 입력해주세요")
    @NotNull(message = "아이디는 필수 입력 항목 입니다", groups = AddValidation.class)
    private Integer userId; //유저ID

    @Size(max = 200, message = "200글자 이하로 작성해주세요", groups = AddValidation.class)
    @NotBlank(message = "내용은 필수 입력 항목 입니다", groups = AddValidation.class)
    private String content; //내용

    @NotBlank(message = "비밀번호는 필수 입력 항목 입니다")
    private String userPwd; //유저PWD

}
