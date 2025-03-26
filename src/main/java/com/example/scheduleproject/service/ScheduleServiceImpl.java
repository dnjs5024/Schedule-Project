package com.example.scheduleproject.service;

import com.example.scheduleproject.entity.Schedule;
import com.example.scheduleproject.exception.CustomException;
import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * 일정ID로 pwd일치하면 db에서 삭제
     *
     * @param scheduleId
     * @param userPwd
     */
    @Override
    public void deleteScheduleById(int scheduleId, String userPwd) {
        checkInsertPwd(scheduleId, userPwd);
        scheduleRepository.deleteScheduleById(scheduleId);
    }

    /**
     * db에 일정 저장
     *
     * @param scheduleRequest
     * @return
     */
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequest) {
        if (scheduleRepository.isUserIdByUserId(scheduleRequest.getUserId()).isEmpty())//users 테이블에 입력한 아이디 있는지 체크
            throw new CustomException("db에 존재하는 id를 입력해주세요");
        return scheduleRepository.saveSchedule(
                initScheduleByScheduleRequestDto(scheduleRequest)//ScheduleRequestDto > Schedule에 넣어줌
        );
    }

    /**
     * 필터링 조건에 의한 검색 서비스
     *
     * @param userName
     * @param updatedAt
     * @param userId
     * @return
     */
    @Override
    public List<ScheduleResponseDto> findSchedules(String userName, String updatedAt, String userId) {//검색 조건에 따라 일정리스트 가져오는 메소드

        List<ScheduleResponseDto> queryResultList = new ArrayList<>();
        //userId가 있으면 실행
        if (!userId.trim().isEmpty()) {
            queryResultList = scheduleRepository.findSchedulesByUserId("%" + userId + "%");

        } else {
            queryResultList = scheduleRepository.findSchedulesByUserName(userName, "%" + updatedAt + "%");
        }
        //userId가 없으면 userName or updatedAt로 검색 하는 것이므로 실행
        if (queryResultList.isEmpty()) throw new CustomException("db에 존재하는 데이터를 입력해주세요");
        return queryResultList;
    }

    /**
     * 선택 일정ID로 db조회 서비스
     *
     * @param scheduleId
     * @return
     */
    @Override
    public ScheduleResponseDto findSelectScheduleById(int scheduleId) {
        return scheduleRepository.findSelectScheduleById(scheduleId);
    }

    /**
     * 업데이트 닉네임 올바른 입력인지 체크
     *
     * @param scheduleId
     * @param scheduleRequest
     * @return db업데이트 후 결과 리턴
     */
    @Override
    public ScheduleResponseDto updateScheduleById(int scheduleId, ScheduleRequestDto scheduleRequest) {

        checkInsertPwd(scheduleId, scheduleRequest.getUserPwd());//비밀번호가 일치하면 업데이트
        if (scheduleRequest.getUserName() == null || scheduleRequest.getUserName().isBlank()) {//올바른 닉네임인지 체크
            throw new CustomException("올바른 형식의 닉네임을 입력하세요");
        } else {
            scheduleRepository.updateScheduleByIdForUserName(scheduleId, initScheduleByScheduleRequestDto(scheduleRequest));//닉네임 업데이트
        }
        return scheduleRepository.updateScheduleByIdForContent(scheduleId, initScheduleByScheduleRequestDto(scheduleRequest));//내용업데이트
    }

    /**
     * 비밀번호 db에서 일정ID로 조회해서 가져오는 메소드
     *
     * @param scheduleId
     * @return db에 저장된 비밀번호
     */
    @Override
    public String findUserPwdById(int scheduleId) {//선택한 일정의 비밀번호를 가져옴
        return scheduleRepository.findUserPwdById(scheduleId);
    }

    /**
     * 페이징 처리
     *
     * @param currentNum 현재 페이지
     * @param pageSize   보여줄 페이지 크기
     * @return 해당 크기만큼 쿼리가져와서 반환
     */
    @Override
    public List<ScheduleResponseDto> findSchedulesByCurrentPageNum(int currentNum, int pageSize) {//페이징
        return scheduleRepository.findSchedulesByCurrentPageNum(currentNum * pageSize, currentNum + pageSize);
    }

    /**
     * 비밀번호 맞는지 검사
     *
     * @param scheduleId
     * @param userPwd
     */
    @Override
    public void checkInsertPwd(int scheduleId, String userPwd) {

        if (!(findUserPwdById(scheduleId).equals(userPwd))) {//비밀번호 맞는지 검사
            throw new CustomException("올바른 비밀번호 입력");
        }
    }

    /**
     * ScheduleRequestDto > Schedule에 할당
     *
     * @param scheduleRequest
     * @return ScheduleRequestDto > Schedule
     */
    @Override
    public Schedule initScheduleByScheduleRequestDto(ScheduleRequestDto scheduleRequest) {
        return new Schedule(
                scheduleRequest.getUserName(),
                scheduleRequest.getUserId(),
                scheduleRequest.getUserPwd(),
                scheduleRequest.getContent()
        );
    }

}
