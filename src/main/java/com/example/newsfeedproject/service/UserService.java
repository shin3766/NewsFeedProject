package com.example.newsfeedproject.service;


import com.example.newsfeedproject.dto.LoginRequestDto;
import com.example.newsfeedproject.dto.SignupRequestDto;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserRoleEnum;
import com.example.newsfeedproject.jwt.JwtUtil;
import com.example.newsfeedproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String intro = requestDto.getIntro();
        String email = requestDto.getEmail();

        // 회원 중복 확인
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        // 사용자 등록
        User user = new User(username, password, email, role, intro);
        userRepository.save(user);//데이터베이스의 한 로우는 해당하는 엔티티 클래스의 한 객체다
    }


}

