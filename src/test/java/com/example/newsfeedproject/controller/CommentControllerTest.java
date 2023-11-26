package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.IntegrationTest;
import com.example.newsfeedproject.dto.CreateCommentRequest;
import com.example.newsfeedproject.dto.UpdateCommentRequest;
import com.example.newsfeedproject.entity.Comment;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.transaction.annotation.Transactional;

import static com.example.newsfeedproject.entity.UserRole.USER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @DisplayName("없는 게시물에 댓글 생성시 실패")
    @Test
    void createCommentFailWhenPostNotExist() throws Exception {
        // given
        SecurityContext context = contextJwtUser(user.getId(), user.getUsername(), user.getRole());
        var request = new CreateCommentRequest(10000L, "test comment");
        // when // then
        mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .with(securityContext(context))
                )
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value("존재하지 않은 게시물입니다.")
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

    @DisplayName("댓글 삭제 성공")
    @Test
    void deleteComment() throws Exception {
        // given
        Comment comment = saveComment("test comment", user, post);
        SecurityContext context = contextJwtUser(user.getId(), user.getUsername(), user.getRole());
        // when // then
        mockMvc.perform(delete("/api/v1/comment/" + comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(securityContext(context))
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.message").value("댓글 삭제 성공")
                );
    }

    @DisplayName("로그인 하지 않은 경우 댓글 삭제 실패")
    @Test
    void deleteCommentWhenNotLogin() throws Exception {
        // given
        Comment comment = saveComment("test comment", user, post);
        // when // then
        mockMvc.perform(delete("/api/v1/comment/" + comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("$.message").value("권한이 없습니다.")
                );
    }

    @DisplayName("자신이 작성하지 않은 댓글 삭제 실패")
    @Test
    void deleteCommentWhenAuthorIsNotLoginUser() throws Exception {
        // given
        User author = saveUser("정석", "1234", "tes@fd.com", USER);
        Comment comment = saveComment("test comment", author, post);
        SecurityContext context = contextJwtUser(user.getId(), user.getUsername(), user.getRole());
        // when // then
        mockMvc.perform(delete("/api/v1/comment/" + comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(securityContext(context))
                )
                .andDo(print())
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("$.message").value("댓글 삭제 권한이 없습니다.")
                );
    }

    @DisplayName("댓글 목록 조회")
    @Test
    void getComments() throws Exception {
        // given
        for (int i = 0; i < 100; i++) saveComment("test comment" + i, user, post);
        // when // then
        mockMvc.perform(get("/api/v1/comment/" + post.getId())
                        .queryParam("size", "11")
                        .queryParam("page", "0")
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.data").exists(),
                        jsonPath("$.totalElement").value(100),
                        jsonPath("$.totalPage").value(10),
                        jsonPath("$.currentPage").value(0),
                        jsonPath("$.size").value(11)
                );
    }
}