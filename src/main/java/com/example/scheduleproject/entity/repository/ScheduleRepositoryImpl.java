package com.example.scheduleproject.entity.repository;

import com.example.scheduleproject.dto.ScheduleResponseDto;
import com.example.scheduleproject.entity.Schedule;
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

    /**
     * //일정 정보 저장하는 메소드
     *
     * @param schedule
     * @return findSelectScheduleById에서 scheduleId로 조회 후 반환
     */
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        Map<String, Object> insertDataMap = new HashMap<>();//데이터 넣어줄 임시 map

        simpleJdbcInsert.withTableName("schedule");//테이블 이름
        simpleJdbcInsert.usingGeneratedKeyColumns("schedule_id");//일정 테이블의 일정ID

        insertDataMap.put("user_id", schedule.getUserId());
        insertDataMap.put("createdAt", getCurrentTime());
        insertDataMap.put("updatedAt", getCurrentTime());
        insertDataMap.put("content", schedule.getContent());
        insertDataMap.put("userPwd", schedule.getUserPwd());

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(insertDataMap));//sql 실행

        return findSelectScheduleById(key.intValue());// db에 저장된 값 조회에서 보여줌

    }

    /**
     * //일정ID로 일정 가져오는 메소드
     *
     * @param scheduleId
     * @return
     */
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

    /**
     * //선택한 일정 삭제하는 메소드
     *
     * @param scheduleId
     */
    @Override
    public void deleteScheduleById(int scheduleId) {
        jdbcTemplate.update("delete from schedule where schedule_id = ? ", scheduleId);
    }

    /**
     * 유저 아이디 존재하는지
     *
     * @param userId
     * @return userId 반환
     */
    @Override
    public List<Integer> isUserIdByUserId(int userId) {
        return jdbcTemplate.query("select user_id from users where user_id =  ? ",
                (rs, i) -> rs.getInt("user_id"), userId);
    }

    /**
     * //선택한 유저ID로 유저가 작성한 글 목록 가져옴
     *
     * @param userId
     * @return List<ScheduleResponseDto> 조회결과
     */
    @Override
    public List<ScheduleResponseDto> findSchedulesByUserId(String userId) {
        return jdbcTemplate.query(
                "select * from " +
                        "(Select * from schedule a  where user_id like ? ) a " +
                        "join " + "(select user_name,user_id from users) b " +
                        "on a.user_id = b.user_id order by updated_at desc;",
                custonRowMapper(), userId);
    }

    /**
     * 유저닉네임으로 일정검색
     *
     * @param userName
     * @param updatedAt
     * @return List<ScheduleResponseDto> 조회결과
     */
    @Override
    public List<ScheduleResponseDto> findSchedulesByUserName(String userName, String updatedAt) {//유저 닉네임과 날짜로 조회에서 리스트 가져오는 메소드
        return jdbcTemplate.query(
                "select * from " +
                        "(Select * from schedule a  where updated_at like ?) a " +
                        "join " + "(select user_name,user_id from users where user_name like if(? = '','%%',?)) b " +
                        "on a.user_id = b.user_id order by updated_at desc;",
                custonRowMapper(), updatedAt, userName, userName);
    }

    /**
     * 업데이트 유저 이름
     *
     * @param scheduleId
     * @param schedule
     * @return findSelectScheduleById에서 조회에서 ScheduleResponseDto반환
     */
    @Override
    public ScheduleResponseDto updateScheduleByIdForUserName(int scheduleId, Schedule schedule) {//일정ID로  작성자명 수정하는 메소드
        jdbcTemplate.update(
                "update users\n" +
                        "set user_name = ?,updated_at = ?\n" +
                        "where user_id = (select user_id\n" +
                        "from schedule\n" +
                        "where schedule_id = ?);",
                schedule.getUserName(), getCurrentTime(), scheduleId);
        return findSelectScheduleById(scheduleId);// 수정 결과 db에 조회에서 보여줌
    }

    /**
     * 업데이트 내용
     *
     * @param scheduleId
     * @param schedule
     * @return
     */
    @Override
    public ScheduleResponseDto updateScheduleByIdForContent(int scheduleId, Schedule schedule) {//일정ID로 할 일 내용 수정하는 메소드
        jdbcTemplate.update(
                "update schedule set content = ?,updated_at = ? where schedule_id = ?  ",
                schedule.getContent(), getCurrentTime(), scheduleId);
        return findSelectScheduleById(scheduleId);//
    }

    /**
     * 일정ID로 해당 글 비번 가져옴
     *
     * @param scheduleId
     * @return 비밀번호
     */
    @Override
    public String findUserPwdById(int scheduleId) {
        return jdbcTemplate.query("Select user_pwd from schedule where schedule_id = ?",
                (rs, i) -> new String(rs.getString("user_pwd")), scheduleId).get(0);
    }

    /**
     * 페이징 limit로 범위 계산
     *
     * @param startNum
     * @param endNum
     * @return
     */
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

    /**
     * 현재 시간 리턴해줌
     *
     * @return
     */
    @Override
    public String getCurrentTime() {//현재 시간 계산해서 가져옴
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 조회결과 여러개일 경우 사용
     *
     * @return List<ScheduleResponseDto> 조회결과
     */
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
