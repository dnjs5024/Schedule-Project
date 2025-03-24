package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    public void deleteScheduleById(int scheduleId, String userPwd);

    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequest);

    public List<ScheduleResponseDto> findSchedules(String userName, String updatedAt, String userId);

    public ScheduleResponseDto findSelectScheduleById(int scheduleId);

    public ScheduleResponseDto updateScheduleById(int scheduleId, ScheduleRequestDto scheduleRequest);

    public String findUserPwdById(int scheduleId, String userPwd);// 비밀번호 db에서 가져옴

    public List<ScheduleResponseDto> findSchedulesByCurrentPageNum(int currentNum,int pageSize);

}
