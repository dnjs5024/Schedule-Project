package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.UsersRequestDto;
import com.example.scheduleproject.exception.CustomException;
import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void deleteScheduleById(int scheduleId, String userPwd) {
        checkInsertPwd(scheduleId,userPwd);
        scheduleRepository.deleteScheduleById(scheduleId);
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequest) {
        return scheduleRepository.saveSchedule(scheduleRequest);
    }

    @Override
    public List<ScheduleResponseDto> findSchedules(String userName, String updatedAt, String userId) {
        if (!userId.trim().isEmpty()) {//userId가 있으면 실행
            return scheduleRepository.findSchedulesByUserId("%" + userId + "%");
        }
        return scheduleRepository.findSchedulesByUserName("%" + userName + "%", "%" + updatedAt + "%"); //userId가 없으면 userName or updatedAt로 검색 하는 것이므로 실행
    }

    @Override
    public ScheduleResponseDto findSelectScheduleById(int scheduleId) {
        return scheduleRepository.findSelectScheduleById(scheduleId);
    }

    @Override
    public ScheduleResponseDto updateScheduleById(int scheduleId, ScheduleRequestDto scheduleRequest) {
        checkInsertPwd(scheduleId,scheduleRequest.getUserPwd());
        return scheduleRepository.updateScheduleById(scheduleId, scheduleRequest);
    }

    @Override
    public String findUserPwdById(int scheduleId) {//선택한 일정의 비밀번호를 가져옴
        return scheduleRepository.findUserPwdById(scheduleId);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByCurrentPageNum(int currentNum, int pageSize) {
        return scheduleRepository.findSchedulesByCurrentPageNum(currentNum * pageSize, currentNum + pageSize);
    }

    @Override
    public void checkInsertPwd(int scheduleId, String userPwd) {
        if (!(findUserPwdById(scheduleId).equals("userPwd"))) {//비밀번호 맞는지 검사
            throw new CustomException("올바른 비밀번호 입력");
        }
    }

    @Override
    public ResponseEntity<UsersRequestDto> userSignUp(UsersRequestDto usersRequestDto) {
        return scheduleRepository.userSignUp(usersRequestDto);
    }
}
