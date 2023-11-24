package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.IntegrationTest;
import com.example.newsfeedproject.dto.CreateCommentRequest;
import com.example.newsfeedproject.dto.UpdateCommentRequest;
import com.example.newsfeedproject.entity.Comment;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.transaction.annotation.Transactional;

import static com.example.newsfeedproject.entity.UserRole.USER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@DisplayName("댓글 API 통합 테스트")
class CommentControllerTest extends IntegrationTest {

    User user;
    Post post;

    @BeforeEach
    void init() {
        user = saveUser("한정석", "1234", "test@gmail.com", USER);
        post = savePost("test post", "test content", user);
    }

    @DisplayName("댓글 생성 성공")
    @Test
    void createComment() throws Exception {
        // given
        SecurityContext context = contextJwtUser(user.getId(), user.getUsername(), user.getRole());
        var request = new CreateCommentRequest(post.getId(), "test comment");
        // when // then
        mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .with(securityContext(context))
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").exists(),
                        jsonPath("$.content").value(request.content()),
                        jsonPath("$.author").value(user.getUsername()),
                        jsonPath("$.createdAt").exists()
                );
    }

    @DisplayName("로그인 하지 않은 경우 댓글 생성 실패")
    @Test
    void createCommentWhenNotLogin() throws Exception {
        // given
        var request = new CreateCommentRequest(post.getId(), "test comment");
        // when // then
        mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("$.message").value("권한이 없습니다.")
                );
    }

    @DisplayName("댓글 수정 성공")
    @Test
    void updateComment() throws Exception {
        // given
        Comment comment = saveComment("test comment", user, post);
        var request = new UpdateCommentRequest(comment.getId(), "after");
        SecurityContext context = contextJwtUser(user.getId(), user.getUsername(), user.getRole());
        // when // then
        mockMvc.perform(patch("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .with(securityContext(context))
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(comment.getId()),
                        jsonPath("$.content").value(request.content()),
                        jsonPath("$.author").value(user.getUsername()),
                        jsonPath("$.createdAt").exists()
                );
    }

    @DisplayName("로그인 하지 않은 경우 댓글 수정 실패")
    @Test
    void updateCommentFailWhenNotLogin() throws Exception {
        // given
        Comment comment = saveComment("test comment", user, post);
        var request = new UpdateCommentRequest(comment.getId(), "after");
        // when // then
        mockMvc.perform(patch("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("$.message").value("권한이 없습니다.")
                );
    }

    @DisplayName("자신이 작성하지 않은 댓글 수정 실패")
    @Test
    void updateCommentWhenLoginUserIsNotAuthor() throws Exception {
        // given
        User author = saveUser("정석", "1234", "tes@fd.com", USER);
        Comment comment = saveComment("test comment", author, post);
        var request = new UpdateCommentRequest(comment.getId(), "after");
        SecurityContext context = contextJwtUser(user.getId(), user.getUsername(), user.getRole());
        // when // then
        mockMvc.perform(patch("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .with(securityContext(context))
                )
                .andDo(print())
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("$.message").value("댓글 수정 권한이 없습니다.")
                );
    }
}