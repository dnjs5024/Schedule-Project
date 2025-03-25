package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
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

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/v1/schedules")
    public List<ScheduleResponseDto> findSchedulesController(
            @RequestParam(required = false, defaultValue = "") String userName,
            @RequestParam(required = false, defaultValue = "") String updatedAt,
            @RequestParam(required = false, defaultValue = "") String userId
    ) {
        return scheduleService.findSchedules(userName, updatedAt, userId);
    }
    @GetMapping("/v1/schedule/{schedulesId}")
    public ScheduleResponseDto findSelectScheduleByIdController(@PathVariable int schedulesId) {
        return scheduleService.findSelectScheduleById(schedulesId);
    }

    /**
     *
     * @param scheduleRequest
     * {
     * "content": 필수 입력
     * "userPwd": 필수 입력
     * "userId":  필수 입력, users table에 없으면 조회 x
     * }
     * @return db에서 ScheduleResponseDto에 넣어주고 반환
     */
    @PostMapping("/v1/schedule")//일정 저장하는 메소드
    public ScheduleResponseDto saveScheduleController(@RequestBody @Valid ScheduleRequestDto scheduleRequest) {
        return scheduleService.saveSchedule(scheduleRequest);
    }

    @DeleteMapping("/v1/schedule")
    public void deleteScheduleByIdController(@RequestParam int schedulesId, @RequestParam String userPwd) {
        scheduleService.deleteScheduleById(schedulesId, userPwd);
    }

    @PatchMapping("/v1/schedule/{schedulesId}")//일정에서 작성자,할일 수정하는 컨트롤러 둘중하나는 입력되야함
    public ScheduleResponseDto updateScheduleByIdController(
            @RequestBody ScheduleRequestDto scheduleRequest,
            @PathVariable @Min(value = 1, message = "1 이상의 값을 입력해주세요") int schedulesId) {
        return scheduleService.updateScheduleById(schedulesId, scheduleRequest);
    }

    @GetMapping("/v1/schedule/{currentNum}/{pageSize}")
    public List<ScheduleResponseDto> findSchedulesByCurrentPageNum(@PathVariable int currentNum, @PathVariable int pageSize) {
        return scheduleService.findSchedulesByCurrentPageNum(currentNum, pageSize);
    }


}
