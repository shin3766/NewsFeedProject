package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.PageDto;
import com.example.newsfeedproject.dto.PostSearchConditionParam;
import com.example.newsfeedproject.repository.PostDynamicRepository;
import com.example.newsfeedproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostDynamicRepository postDynamicRepository;

    public PageDto getPostList(PostSearchConditionParam condition) {
        return postDynamicRepository.findListByCondition(condition);
    }
}
