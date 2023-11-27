package com.example.newsfeedproject.entity;

import com.example.newsfeedproject.dto.UpdateCommentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.newsfeedproject.entity.UserRole.USER;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("댓글 도메인 테스트")
class CommentTest {

    @DisplayName("댓글 작성자라면 댓글을 수정할 수 있다.")
    @Test
    void updateComment() {
        // given
        var request = new UpdateCommentRequest(1L, "after content");
        var user = User.builder()
                .username("홍정기")
                .password("1234")
                .email("spa@test.com")
                .role(USER)
                .intro("intro")
                .build();
        var comment = Comment.builder()
                .user(user)
                .content("before content")
                .build();
        // when
        comment.update(request);
        // then
        assertThat(comment.getContent()).isEqualTo("after content");
    }

    @DisplayName("댓글 수정시 내용이 null 이라면 갱신하지 않는다.")
    @Test
    void updateCommentFailWhenContentIsNull() {
        // given
        var request = new UpdateCommentRequest(1L, null);
        var user = User.builder()
                .username("홍정기")
                .password("1234")
                .email("spa@test.com")
                .role(USER)
                .intro("intro")
                .build();
        var comment = Comment.builder()
                .user(user)
                .content("before content")
                .build();
        // when
        comment.update(request);
        // then
        assertThat(comment.getContent()).isEqualTo("before content");
    }

    @DisplayName("댓글 수정시 내용이 비어있다면 갱신하지 않는다.")
    @Test
    void updateCommentFailWhenContentIsEmpty() {
        // given
        var request = new UpdateCommentRequest(1L, "");
        var user = User.builder()
                .username("홍정기")
                .password("1234")
                .email("spa@test.com")
                .role(USER)
                .intro("intro")
                .build();
        var comment = Comment.builder()
                .user(user)
                .content("before content")
                .build();
        // when
        comment.update(request);
        // then
        assertThat(comment.getContent()).isEqualTo("before content");
    }
}