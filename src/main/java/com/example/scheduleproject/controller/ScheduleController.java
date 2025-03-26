package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.groups.AddValidation;
import com.example.scheduleproject.service.ScheduleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/schedule")
public class ScheduleController {//schedule table 컨트롤러

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {//scheduleService 주입
        this.scheduleService = scheduleService;
    }

    /**
     * 필터링 조건에 의한 검색 컨트롤러 할일, 작성자명, 비밀번호, 작성/수정일을 저장
     * 작성자명은 users id로 users db에서 가져옴 users 테이블에 정보 저장해야 가능
     *
     * @param userName  없어도 조회 o
     * @param updatedAt 없어도 조회 o
     * @param userId    userId가 있으면 id를 통해서 조회 다른 매개변수 사용 x
     * @return 필터링 조건에 대한 결과값
     */
    @GetMapping("/v1/schedules")
    public List<ScheduleResponseDto> findSchedulesController(
            @RequestParam(required = false, defaultValue = "") String userName,
            @RequestParam(required = false, defaultValue = "") String updatedAt,
            @RequestParam(required = false, defaultValue = "") String userId
    ) {
        return scheduleService.findSchedules(userName, updatedAt, userId);
    }

    /**
     * 일정ID를 매개변수로 받고 일정ID로 조회
     *
     * @param schedulesId
     * @return 일정ID로 조회 결과
     */
    @GetMapping("/v1/schedule/{schedulesId}")
    public ScheduleResponseDto findSelectScheduleByIdController(@PathVariable int schedulesId) {
        return scheduleService.findSelectScheduleById(schedulesId);
    }

    /**
     * @param scheduleRequest {
     *                        "content": 필수 입력
     *                        "userPwd": 필수 입력
     *                        "userId":  필수 입력, users table에 없으면 조회 x
     *                        }
     * @return db에서 ScheduleResponseDto에 넣어주고 반환
     */
    @PostMapping("/v1/schedule")//일정 저장하는 메소드
    public ScheduleResponseDto saveScheduleController(
            @RequestBody @Validated(AddValidation.class) ScheduleRequestDto scheduleRequest
    ) {
        return scheduleService.saveSchedule(scheduleRequest);
    }

    /**
     * 입력받은 일정ID와 pwd를 통해 pwd 일치하면 삭제 컨트롤러 검증추가 필요
     *
     * @param schedulesId
     * @param scheduleRequest 입력
     */
    @DeleteMapping("/v1/schedule/{schedulesId}")
    public void deleteScheduleByIdController(
            @PathVariable @Min(value = 1, message = "1 이상의 값을 입력해주세요") int schedulesId,
            @RequestBody @Valid ScheduleRequestDto scheduleRequest) {
        scheduleService.deleteScheduleById(schedulesId, scheduleRequest.getUserPwd());
    }

    /**
     * 일정에서 작성자,할일 수정하는 컨트롤러 할일은 필수 입력되야함
     *
     * @param scheduleRequest userName,content 필수로 받음
     * @param schedulesId     1이상 필수 입력
     * @return 수정된 일정 보여줌
     * @Validated(UpdateValidation.class) 추가하면 connent 필수로 받음
     */
    @PatchMapping("/v1/schedule/{schedulesId}")
    public ScheduleResponseDto updateScheduleByIdController(
            @RequestBody @Validated(AddValidation.class) ScheduleRequestDto scheduleRequest,
            @PathVariable @Min(value = 1, message = "1 이상의 값을 입력해주세요") int schedulesId) {
        return scheduleService.updateScheduleById(schedulesId, scheduleRequest);
    }

    /**
     * 원하는 페이지의 일정보여주는 메소드
     *
     * @param currentNum 현재 보이는 페이지 번호
     * @param pageSize   보여줄 일정 개수
     * @return 일정리스트
     */
    @GetMapping("/v1/schedule/{currentNum}/{pageSize}")
    public List<ScheduleResponseDto> findSchedulesByCurrentPageNum(
            @PathVariable @Min(1) int currentNum,
            @PathVariable @Min(1) int pageSize) {
        return scheduleService.findSchedulesByCurrentPageNum(currentNum, pageSize);
    }


}
