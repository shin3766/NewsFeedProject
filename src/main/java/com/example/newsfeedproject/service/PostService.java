package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.postDto.PostRequestDto;
import com.example.newsfeedproject.dto.postDto.PostResponseDto;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // post 등록하기
    public PostResponseDto createPost(PostRequestDto requestDto){
        // 새로운 post객체에 requestDto 넣기
        Post post = new Post(requestDto);

        // DB저장
        Post savePost = postRepository.save(post);

        // post객체 -> responseDto담기
        PostResponseDto postResponseDto = new PostResponseDto(savePost);

        return postResponseDto;
    }

    // post 전체 조회
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        // DB에 해당 post 있는지 확인
        Post post = findPost(id);

        // requestDto로 post객체 업데이트
        post.update(requestDto);

        //id 반환
        return id;
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
