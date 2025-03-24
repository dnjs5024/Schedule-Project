package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.service.ScheduleService;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

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

    @PostMapping("/v1/schedule")
    public ScheduleResponseDto saveScheduleController(@RequestBody ScheduleRequestDto scheduleRequest) {
        return scheduleService.saveSchedule(scheduleRequest);
    }

    @DeleteMapping("/v1/schedule")
    public void deleteScheduleByIdController(@RequestParam int schedulesId, @RequestParam String userPwd) {
        scheduleService.deleteScheduleById(schedulesId, userPwd);
    }

    @PatchMapping("/v1/schedule/{schedulesId}")
    public ScheduleResponseDto updateScheduleByIdController(@RequestBody ScheduleRequestDto scheduleRequest, @PathVariable int schedulesId) {
        return scheduleService.updateScheduleById(schedulesId, scheduleRequest);
    }

    @GetMapping("/v1/schedule/{currentNum}/{pageSize}")
    public List<ScheduleResponseDto> findSchedulesByCurrentPageNum(@PathVariable int currentNum, @PathVariable int pageSize){
        return scheduleService.findSchedulesByCurrentPageNum(currentNum, pageSize);
    }

}
