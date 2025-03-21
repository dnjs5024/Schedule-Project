package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/v1/schedules")
    public List<ScheduleResponseDto> findSchedulesController() {
        return scheduleService.findSchedules();
    }

    @GetMapping("/v1/schedule/{schedulesId}")
    public ScheduleResponseDto findSelectScheduleByIdController(@PathVariable int schedulesId) {
        return scheduleService.findSelectScheduleById(schedulesId);
    }

    @PostMapping("/v1/schedules")
    public ScheduleResponseDto saveScheduleController(@RequestBody ScheduleRequestDto scheduleRequest) {
        return scheduleService.saveSchedule(scheduleRequest);
    }

    @DeleteMapping("/v1/schedule")
    public void deleteScheduleByIdController(@RequestParam int schedulesId, String userPwd) {
        scheduleService.deleteScheduleById(schedulesId, userPwd);
    }

    @PatchMapping("v1/schedule/{schedulesId}")
    public ScheduleResponseDto updateScheduleByIdController(@RequestBody ScheduleRequestDto scheduleRequest) {
        return scheduleService.updateScheduleById(scheduleRequest);
    }

}
