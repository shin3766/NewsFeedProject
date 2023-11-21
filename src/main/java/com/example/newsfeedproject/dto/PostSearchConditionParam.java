package com.example.newsfeedproject.dto;

public record PostSearchConditionParam(
        int size,
        int page,
        String keyword
) {
    public int offset() {
        return 0;
    }
}
