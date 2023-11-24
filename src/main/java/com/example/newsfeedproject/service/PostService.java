package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.JwtUser;
import com.example.newsfeedproject.dto.PageDto;
import com.example.newsfeedproject.dto.PostSearchConditionParam;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.PostDynamicRepository;
import com.example.newsfeedproject.dto.postDto.PostRequestDto;
import com.example.newsfeedproject.dto.postDto.PostResponseDto;
import com.example.newsfeedproject.entity.Post;

import com.example.newsfeedproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostDynamicRepository postDynamicRepository;

    private final UserStatusService userStatusService;



    public PageDto getPostList(PostSearchConditionParam condition) {
        return postDynamicRepository.findListByCondition(condition);
    }

    // post 등록하기
    public PostResponseDto createPost(PostRequestDto requestDto){
        // jwt토큰 생성
        JwtUser loginUser = userStatusService.getLoginUser();

        // 새로운 post객체에 requestDto 넣기
        Post post = Post.builder()
                .content(requestDto.getContent())
                .user(User.foreign(loginUser.id()))
                .title(requestDto.getTitle())
                .build();

        // jwt토큰으로 작성자 이름 조회

        // DB저장
        Post savedPost = postRepository.save(post);

        // post객체 -> responseDto담기
        PostResponseDto postResponseDto = new PostResponseDto(savedPost);

        return postResponseDto;
    }

    // post 선택 조회
    public PostResponseDto getPost(Long id) {
        Post post = findPost(id);

        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;
    }

    // post 업데이트
    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        // DB에 해당 post 있는지 확인
        Post post = findPost(id);

        // requestDto로 post객체 업데이트
        post.update(requestDto);

        //id 반환
        return id;
    }

    // post 선택 삭제
    public Long deletePost(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Post post = findPost(id);

        // post 삭제
        postRepository.delete(post);

        return id;
    }

    // post 찾기
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
