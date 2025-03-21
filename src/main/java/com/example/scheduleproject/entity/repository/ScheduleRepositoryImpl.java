package com.example.scheduleproject.entity.repository;

import com.example.scheduleproject.dto.ScheduleRequestDto;
import com.example.scheduleproject.dto.ScheduleResponseDto;
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
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequest) {
        //현재 시간
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        simpleJdbcInsert.withTableName("schedule");
        simpleJdbcInsert.usingGeneratedKeyColumns("schedule_id");
        Map<String, Object> insertDataMap = new HashMap<>();
        insertDataMap.put("userName", scheduleRequest.getUserName());
        insertDataMap.put("createdAt", localDateTime);
        insertDataMap.put("updatedAt", localDateTime);
        insertDataMap.put("content", scheduleRequest.getContent());
        insertDataMap.put("userPwd", scheduleRequest.getUserPwd());

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(insertDataMap));
        return new ScheduleResponseDto(key.intValue(), scheduleRequest.getUserName(), localDateTime, scheduleRequest.getContent());
    }

    @Override
    public List<ScheduleResponseDto> findSchedules() {
        return jdbcTemplate.query("Select * from schedule", custonRowMapper());
    }

    @Override
    public ScheduleResponseDto findSelectScheduleById(int scheduleId) {
        return jdbcTemplate.query("Select * from schedule where schedule_id = ?", (rs, i) -> new ScheduleResponseDto(rs.getInt("schedule_id"), rs.getString("user_name"), rs.getString("updated_at"), rs.getString("content")), scheduleId).get(0);
    }

    @Override
    public void deleteScheduleById(int scheduleId) {
        jdbcTemplate.update("delete from schedule where schedule_id = ? ", scheduleId);
    }

    @Override
    public ScheduleResponseDto updateScheduleById(ScheduleRequestDto scheduleRequest) {
        jdbcTemplate.update("update set content = ?,user_name = ? from schedule where schedule_id = ?  ", scheduleRequest.getContent(),scheduleRequest.getUserName(),scheduleRequest.getSchedileId());
        return null;
    }

    @Override
    public String findUserPwdById(int scheduleId) {
        return jdbcTemplate.query("Select user_pwd from schedule where schedule_id = ?", (rs, i) -> new String(rs.getString("user_pwd")), scheduleId).get(0);
    }

    public RowMapper<ScheduleResponseDto> custonRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(rs.getInt("schedule_id"), rs.getString("user_name"), rs.getString("updated_at"), rs.getString("content"));
            }
        };
    }
}
