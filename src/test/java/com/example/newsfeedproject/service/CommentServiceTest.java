package com.example.newsfeedproject.service;

import com.example.newsfeedproject.IntegrationTest;
import com.example.newsfeedproject.dto.CommentDto;
import com.example.newsfeedproject.dto.CreateCommentRequest;
import com.example.newsfeedproject.dto.UpdateCommentRequest;
import com.example.newsfeedproject.entity.Comment;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.manager.UserStatusManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;

import static com.example.newsfeedproject.entity.UserRoleEnum.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@DisplayName("댓글 서비스 테스트")
class CommentServiceTest extends IntegrationTest {

    @Autowired
    CommentService commentService;
    @MockBean
    protected UserStatusManager userStatusManager;

    User loginUser;

    @BeforeEach
    void init() {
        loginUser = saveUser("홍정기", "1234", "test@spa.com", USER);
        givenLoginUser(loginUser);
    }

    private void givenLoginUser(User user) {
        given(userStatusManager.getLoginUser())
                .willReturn(user);
    }

    @DisplayName("댓글 생성 성공")
    @Test
    void createComment() {
        // given
        var request = new CreateCommentRequest("content입니다.");
        // when
        CommentDto response = commentService.createComment(request);
        // then
        assertThat(response.author()).isEqualTo("홍정기");
        assertThat(response.content()).isEqualTo("content입니다.");
        assertThat(response.createdAt()).isNotNull();
        assertThat(response.id()).isNotNull();
    }

    @DisplayName("댓글 수정 성공")
    @Test
    void updateComment() {
        // given
        Comment comment = saveComment("test content", loginUser);
        var request = new UpdateCommentRequest(comment.getId(), "after content");
        // when
        CommentDto response = commentService.updateComment(request);
        // then
        assertThat(response.id()).isEqualTo(comment.getId());
        assertThat(response.author()).isEqualTo(loginUser.getUsername());
        assertThat(response.createdAt()).isNotNull();
        assertThat(response.content()).isEqualTo("after content");
    }

    @DisplayName("작성자가 아니라면 댓글 수정 실패한다.")
    @Test
    void updateCommentWhenOtherUserTry() {
        // given
        User author = saveUser("윤여준", "1234", "test1@spa.com", USER);
        Comment comment = saveComment("test content", author);
        var request = new UpdateCommentRequest(comment.getId(), "after content");
        // when // then
        assertThatThrownBy(() -> commentService.updateComment(request))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("댓글 수정 권한이 없습니다.");
    }

    @DisplayName("댓글 삭제 성공")
    @Test
    void deleteComment() {
        // given
        Comment comment = saveComment("test content", loginUser);
        // when
        commentService.deleteComment(comment.getId());
        // then
        assertThat(commentRepository.existsById(comment.getId())).isFalse();
    }

    @DisplayName("작성자와 다른 유저가 댓글 삭제시 실패한다.")
    @Test
    void deleteCommentWhenOtherUserTry() {
        // given
        User author = saveUser("윤여준", "1234", "test1@spa.com", USER);
        Comment comment = saveComment("test content", author);
        // when // then
        assertThatThrownBy(() -> commentService.deleteComment(comment.getId()))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("댓글 삭제 권한이 없습니다.");
        assertThat(commentRepository.existsById(comment.getId())).isTrue();
    }
}