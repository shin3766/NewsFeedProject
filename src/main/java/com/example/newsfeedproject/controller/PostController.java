package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.postDto.PostRequestDto;
import com.example.newsfeedproject.dto.postDto.PostResponseDto;
import com.example.newsfeedproject.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // CREATE: 게시물 작성
    @PostMapping
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // READ: 게시물 전체 조회
    @GetMapping
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    // READ: 게시물 선택 조회
    @GetMapping("/{id}")
    public PostResponseDto getPost() {

    }

    // UPDATE: 선택 게시물 업데이트
    @PatchMapping("/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    // DELETE: 선택 게시물 삭제
    @DeleteMapping("/{id}")
    public Long deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}
}