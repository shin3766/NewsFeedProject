package com.example.newsfeedproject.dto.profiledto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequestDto {
    private String username;
    private String intro;
    private String password;
}
