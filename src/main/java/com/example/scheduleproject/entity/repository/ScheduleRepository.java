package com.example.scheduleproject.entity.repository;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleRepository {

    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequest);

    public List<ScheduleResponseDto> findSchedulesByUserId(String userId);

    public List<ScheduleResponseDto> findSchedulesByUserName(String userName, String updatedAt);

    public ScheduleResponseDto findSelectScheduleById(int scheduleId);

    public void deleteScheduleById(int scheduleId);

    public ScheduleResponseDto updateScheduleById(int scheduleId, ScheduleRequestDto scheduleRequest);

    public String findUserPwdById(int scheduleId);// 비밀번호 db에서 가져옴

    public String getCurrentTime();//현재 시간 가져옴

}
