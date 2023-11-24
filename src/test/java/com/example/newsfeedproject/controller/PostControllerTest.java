package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.postDto.PostRequestDto;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.repository.CommentRepository;
import com.example.newsfeedproject.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc // 웹 어플리케이션에서 컨트롤러를 테스트할 때, 서블릿 컨테이너를 모킹하기위해 사용
@SpringBootTest
@Transactional
class PostControllerTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @DisplayName("POST 요청 시 DB에 값 저장되는지 확인하기")
    @Test
    void createPost () throws Exception {
        //given
        var title = "제목입니다";
        var content = "내용입니다";
        var request = new PostRequestDto(title, content);
        //when //then
        mockMvc.perform(post("/api/v1/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print()
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.id").exists(),
                jsonPath("$.title").value(title),
                jsonPath("$.content").value(content),
                jsonPath("$.createdAt").exists(),
                jsonPath("$.activatedAt").exists()
        );

    }

    @Test
    void getPost() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void deletePost() {
    }
}