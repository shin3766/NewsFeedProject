package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.profiledto.ProfileRequestDto;
import com.example.newsfeedproject.dto.profiledto.ProfileResponseDto;
import com.example.newsfeedproject.entity.ProfileUser;
import com.example.newsfeedproject.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    // 생성자 주입을 이용하여 ProfileRepository 주입
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Transactional
    public void updateProfile(Long id, ProfileRequestDto requestDto) {
        ProfileUser profileUser = profileRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        // DTO에서 변경된 필드만 업데이트
        if (requestDto.getUsername() != null) {
            profileUser.setUsername(requestDto.getUsername());
        }

        if (requestDto.getContents() != null) {
            profileUser.setContents(requestDto.getContents());
        }

        if (requestDto.getEmail() != null) {
            profileUser.setEmail(requestDto.getEmail());
        }

        // 다른 필드도 필요에 따라 추가 업데이트 가능

        profileRepository.save(profileUser);
    }


    public ProfileResponseDto getProfile(Long id) {
        // 해당 ID에 대한 프로필을 찾음
        ProfileUser profileUser = findProfile(id);

        // ProfileUser를 ProfileResponseDto로 변환하여 반환
        return new ProfileResponseDto(profileUser);
    }

    private ProfileUser findProfile(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로파일이 존재하지 않습니다."));
    }
}
