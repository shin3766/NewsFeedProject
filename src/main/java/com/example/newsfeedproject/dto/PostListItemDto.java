package com.example.newsfeedproject.dto;

import com.example.newsfeedproject.entity.Post;

import java.time.LocalDateTime;

public record PostListItemDto(
       Long id,
       String title,
       LocalDateTime createdAt

) {
}
