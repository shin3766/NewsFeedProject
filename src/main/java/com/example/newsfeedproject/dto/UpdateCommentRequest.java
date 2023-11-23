package com.example.newsfeedproject.dto;

public record UpdateCommentRequest(
        Long id,
        String content
) {
}
