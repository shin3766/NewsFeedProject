package com.example.newsfeedproject.dto.profiledto;

import com.example.newsfeedproject.entity.ProfileUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
    private Long id;
    private String username;
    private String intro;
    private String email;


    // ProfileUser를 받아서 ProfileResponseDto로 변환하는 생성자 추가
    public ProfileResponseDto(ProfileUser profileUser) {
        this.id = profileUser.getId();
        this.username = profileUser.getUsername();
        this.intro = profileUser.getIntro();
        this.email = profileUser.getEmail();
    }
}