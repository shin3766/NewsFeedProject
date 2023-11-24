package com.example.newsfeedproject.dto;

import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserRoleEnum;

public record JwtUser(
        Long id,
        String username,
        String email,
        UserRoleEnum role
) {

    public static JwtUser of(User user){
        return new JwtUser(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
}
