package com.example.newsfeedproject.dto;

import java.util.List;

public record PageDto(
        List<?> data,
        long totalElement,
        long totalPage,
        int currentPage,
        int size
) {
}
