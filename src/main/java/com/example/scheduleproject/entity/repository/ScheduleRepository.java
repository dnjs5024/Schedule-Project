package com.example.scheduleproject.entity.repository;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.dto.UsersRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ScheduleRepository {

    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequest);

    public List<ScheduleResponseDto> findSchedulesByUserId(String userId);

    public List<ScheduleResponseDto> findSchedulesByUserName(String userName, String updatedAt);

    public ScheduleResponseDto findSelectScheduleById(int scheduleId);

    public void deleteScheduleById(int scheduleId);

    public ScheduleResponseDto updateScheduleByIdForUserName(int scheduleId, ScheduleRequestDto scheduleRequest);//아이디가져와서 유저 닉네임 업데이트

    public ScheduleResponseDto updateScheduleByIdForContent(int scheduleId, ScheduleRequestDto scheduleRequest);//아이디가져와서 일정 내용 업데이트

    public String findUserPwdById(int scheduleId);// 비밀번호 db에서 가져옴

    public String getCurrentTime();//현재 시간 가져옴

    public List<ScheduleResponseDto> findSchedulesByCurrentPageNum(int startNum,int endNum);

    public ResponseEntity<UsersRequestDto> userSignUp(UsersRequestDto usersRequestDto);//유저 정보 users 테이블에 저장

}
