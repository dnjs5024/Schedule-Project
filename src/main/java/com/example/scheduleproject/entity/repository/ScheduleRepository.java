package com.example.scheduleproject.entity.repository;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleRepository {
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequest);
    public List<ScheduleResponseDto> findSchedules();
    public ScheduleResponseDto findSelectScheduleById(int scheduleId);
    public void deleteScheduleById(int scheduleId);
    public ScheduleResponseDto updateScheduleById(ScheduleRequestDto scheduleRequest);

    public String findUserPwdById(int scheduleId);// 비밀번호 db에서 가져옴
}
