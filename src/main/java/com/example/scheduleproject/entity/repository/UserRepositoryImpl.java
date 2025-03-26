package com.example.scheduleproject.entity.repository;

import com.example.scheduleproject.dto.UsersResponseDto;
import com.example.scheduleproject.entity.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {


    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ResponseEntity<UsersResponseDto> userSignUp(Users users) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        Map<String, Object> map = new HashMap<>();

        simpleJdbcInsert.withTableName("users");//테이블
        simpleJdbcInsert.usingGeneratedKeyColumns("user_id");// auto 번호 키

        map.put("user_name", users.getUserName());
        map.put("user_email", users.getUserEmail());
        map.put("created_at", getCurrentTime());
        map.put("updated_at", getCurrentTime());

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(map));//쿼리 실행 후 키 반환

        return new ResponseEntity<>(new UsersResponseDto(
                key.intValue(),
                users.getUserName(),
                users.getUserEmail(),
                getCurrentTime(),
                getCurrentTime()
        ),HttpStatus.OK);
    }

    @Override
    public String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
