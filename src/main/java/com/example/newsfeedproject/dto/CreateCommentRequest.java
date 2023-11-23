package com.example.newsfeedproject.dto;

public record CreateCommentRequest(
        Long postId,
        String content
) {
}
