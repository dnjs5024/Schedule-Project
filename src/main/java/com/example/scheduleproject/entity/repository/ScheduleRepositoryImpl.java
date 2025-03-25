package com.example.scheduleproject.entity.repository;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.dto.UsersRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequest) {//일정 정보 저장하는 메소드

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("schedule");//테이블 이름
        simpleJdbcInsert.usingGeneratedKeyColumns("schedule_id");//일정 테이블의 일정ID
        Map<String, Object> insertDataMap = new HashMap<>();//데이터 넣어줄 임시 map
        insertDataMap.put("user_id", scheduleRequest.getUserId());
        insertDataMap.put("createdAt", getCurrentTime());
        insertDataMap.put("updatedAt", getCurrentTime());
        insertDataMap.put("content", scheduleRequest.getContent());
        insertDataMap.put("userPwd", scheduleRequest.getUserPwd());

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(insertDataMap));//sql 실행
        return findSelectScheduleById(key.intValue());// db에 저장된 값 조회에서 보여줌
    }


    @Override
    public ScheduleResponseDto findSelectScheduleById(int scheduleId) {//일정ID로 일정 가져오는 메소드
        return jdbcTemplate.query(
                "select * from\n" +
                        "(select * from schedule where schedule_id = ?)a\n" +
                        "join\n" +
                        "(select user_name,user_id from users)b\n" +
                        "on a.user_id = b.user_id;",
                (rs, i) -> new ScheduleResponseDto(
                        rs.getInt("schedule_id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("updated_at"),
                        rs.getString("content")
                ), scheduleId).get(0);
    }

    @Override
    public void deleteScheduleById(int scheduleId) {//선택한 일정 삭제하는 메소드
        jdbcTemplate.update("delete from schedule where schedule_id = ? ", scheduleId);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByUserId(String userId) {//선택한 유저ID로 유저가 작성한 글 목록 가져옴
        return jdbcTemplate.query(
                "select * from " +
                        "(Select * from schedule a  where user_id like ? ) a " +
                        "join " + "(select user_name,user_id from users) b " +
                        "on a.user_id = b.user_id order by updated_at desc;",
                custonRowMapper(), userId);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByUserName(String userName, String updatedAt) {//유저 닉네임과 날짜로 조회에서 리스트 가져오는 메소드
        return jdbcTemplate.query(
                "select * from " +
                        "(Select * from schedule a  where updated_at like ?) a " +
                        "join " + "(select user_name,user_id from users where user_name like ?) b " +
                        "on a.user_id = b.user_id order by updated_at desc;",
                custonRowMapper(), updatedAt, userName);
    }

    @Override
    public ScheduleResponseDto updateScheduleByIdForUserName(int scheduleId, ScheduleRequestDto scheduleRequest) {//일정ID로  작성자명 수정하는 메소드
        jdbcTemplate.update(
                "update users\n" +
                        "set user_name = 'updateName'\n" +
                        "where user_id = (select user_id\n" +
                        "from schedule\n" +
                        "where schedule_id = ?);",
                scheduleId);
        return findSelectScheduleById(scheduleId);// 수정 결과 db에 조회에서 보여줌
    }

    @Override
    public ScheduleResponseDto updateScheduleByIdForContent(int scheduleId, ScheduleRequestDto scheduleRequest) {//일정ID로 할 일 내용 수정하는 메소드
        jdbcTemplate.update(
                "update schedule set content = ?,updated_at = ? where schedule_id = ?  ",
                scheduleRequest.getContent(), getCurrentTime(), scheduleId);
        return findSelectScheduleById(scheduleId);//
    }

    @Override
    public String findUserPwdById(int scheduleId) {
        return jdbcTemplate.query("Select user_pwd from schedule where schedule_id = ?",
                (rs, i) -> new String(rs.getString("user_pwd")), scheduleId).get(0);
    }

    @Override
    public String getCurrentTime() {//현재 시간 계산해서 가져옴
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByCurrentPageNum(int startNum, int endNum) {
        return jdbcTemplate.query(
                "select *\n" +
                        "from (Select * from schedule a) a\n" +
                        "join (select user_name, user_id from users) b\n" +
                        "on a.user_id = b.user_id\n" +
                        "order by updated_at desc\n" +
                        "limit ?,?",
                custonRowMapper(), startNum, endNum);
    }

    @Override
    public ResponseEntity<UsersRequestDto> userSignUp(UsersRequestDto usersRequestDto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("users");
        simpleJdbcInsert.usingGeneratedKeyColumns("user_id");
        Map<String, Object> map = new HashMap<>();
        return null;
    }

    public RowMapper<ScheduleResponseDto> custonRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getInt("schedule_id"),
                        rs.getInt("a.user_id"),
                        rs.getString("user_name"),
                        rs.getString("updated_at"),
                        rs.getString("content"));
            }
        };
    }
}
