package com.example.newsfeedproject.dto;

import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserRole;

public record JwtUser(
        Long id,
        String username,
        String email,
        UserRole role
) {

    public static JwtUser of(User user){
        return new JwtUser(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
}
