package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
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
    public void deleteScheduleById(int schedule, String userPwd) {
        if (findUserPwdById(schedule, userPwd).equals("userPwd"))
            scheduleRepository.deleteScheduleById(schedule);  //비밀번호 맞는지 검사
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
        return scheduleRepository.findSchedulesByUserName("%" + userName + "%" ,"%" + updatedAt + "%"); //userId가 없으면 userName or updatedAt로 검색 하는 것이므로 실행
    }

    @Override
    public ScheduleResponseDto findSelectScheduleById(int scheduleId) {
        return scheduleRepository.findSelectScheduleById(scheduleId);
    }

    @Override
    public ScheduleResponseDto updateScheduleById(int scheduleId, ScheduleRequestDto scheduleRequest) {
        return scheduleRepository.updateScheduleById(scheduleId, scheduleRequest);
    }

    @Override
    public String findUserPwdById(int scheduleId, String userPwd) {
        return scheduleRepository.findUserPwdById(scheduleId);
    }
}
