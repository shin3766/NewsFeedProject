package com.example.newsfeedproject.dto;

import com.example.newsfeedproject.entity.Comment;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        String content,
        String author,
        LocalDateTime createdAt
) {
    public static CommentDto of(Comment c) {
        return new CommentDto(c.getId(), c.getContent(), c.getAuthor(), c.getCreatedAt());
    }
}
