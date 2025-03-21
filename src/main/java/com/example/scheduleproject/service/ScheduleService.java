package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    public void deleteScheduleById(int scheduleId,String userPwd);
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequest);
    public List<ScheduleResponseDto> findSchedules( );
    public ScheduleResponseDto findSelectScheduleById(int scheduleId);
    public ScheduleResponseDto updateScheduleById(ScheduleRequestDto scheduleRequest);
    public String findUserPwdById(int scheduleId,String userPwd);// 비밀번호 db에서 가져옴
}
