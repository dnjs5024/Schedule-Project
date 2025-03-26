package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.UsersRequestDto;
import com.example.scheduleproject.dto.UsersResponseDto;
import com.example.scheduleproject.entity.Users;
import com.example.scheduleproject.entity.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원가입 서비스 usersRequestDto > Users db로 넘겨줌
     * @param usersRequestDto
     * @return ResponseEntity<UsersResponseDto>
     */
    @Override
    public ResponseEntity<UsersResponseDto> userSignUp(UsersRequestDto usersRequestDto) {
        return userRepository.userSignUp(
                new Users(
                        usersRequestDto.getUserName(),
                        usersRequestDto.getUserEmail()
                )
        );
    }
}
