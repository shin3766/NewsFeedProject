package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.postDto.PostRequestDto;
import com.example.newsfeedproject.dto.postDto.PostResponseDto;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto requestDto){
        // 새로운 post객체에 requestDto 넣기
        Post post = new Post(requestDto);

        // DB저장
        Post savePost = postRepository.save(post);

        // post객체 -> responseDto담기
        PostResponseDto postResponseDto = new PostResponseDto(savePost);

        return postResponseDto;
    }
}
