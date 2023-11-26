package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.profiledto.ProfileRequestDto;
import com.example.newsfeedproject.dto.profiledto.ProfileResponseDto;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final UserRepository userRepository;

    @Transactional
    public ProfileResponseDto updateProfile(Long id, ProfileRequestDto requestDto) {
        User profileUser = userRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        profileUser.update(requestDto);
        return new ProfileResponseDto(profileUser);
    }


    public ProfileResponseDto getProfile(Long id) {
        // 해당 ID에 대한 프로필을 찾음
        User user = findProfile(id);
        // ProfileUser를 ProfileResponseDto로 변환하여 반환
        return new ProfileResponseDto(user);
    }

    private User findProfile(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로파일이 존재하지 않습니다."));
    }
}
