package com.example.scheduleproject.entity.repository;

import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.dto.UsersRequestDto;
import com.example.scheduleproject.entity.Schedule;
import com.example.scheduleproject.entity.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ScheduleRepository {

    public ScheduleResponseDto saveSchedule(Schedule schedule);

    public List<ScheduleResponseDto> findSchedulesByUserId(String userId);

    public List<ScheduleResponseDto> findSchedulesByUserName(String userName, String updatedAt);

    public ScheduleResponseDto findSelectScheduleById(int scheduleId);

    public void deleteScheduleById(int scheduleId);

    public List<Integer> isUserIdByUserId(int userId);//users 테이블에 해당 user_id가 있는지

    public ScheduleResponseDto updateScheduleByIdForUserName(int scheduleId, Schedule schedule);//아이디가져와서 유저 닉네임 업데이트

    public ScheduleResponseDto updateScheduleByIdForContent(int scheduleId, Schedule schedule);//아이디가져와서 일정 내용 업데이트

    public String findUserPwdById(int scheduleId);// 비밀번호 db에서 가져옴

    public String getCurrentTime();//현재 시간 가져옴

    public List<ScheduleResponseDto> findSchedulesByCurrentPageNum(int startNum,int endNum);



}
