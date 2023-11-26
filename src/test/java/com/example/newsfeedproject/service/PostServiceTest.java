package com.example.newsfeedproject.service;

import com.example.newsfeedproject.IntegrationTest;
import com.example.newsfeedproject.dto.JwtUser;
import com.example.newsfeedproject.dto.postDto.PostRequestDto;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.example.newsfeedproject.entity.UserRole.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class PostServiceTest extends IntegrationTest {

    @Autowired
    PostService postService;

    @MockBean
    UserStatusService userStatusService;

    @Test
    void test1() {
        // given
        var request = new PostRequestDto("title", "content");
        User user = saveUser("신유섭", "1234", "test@spa.com", USER);
        given(userStatusService.getLoginUser()).willReturn(JwtUser.of(user));
        // when
        var response = postService.createPost(request);
        // then
        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getContent()).isEqualTo("content");
        assertThat(response.getId()).isNotNull();
        assertThat(response.getCreatedAt()).isNotNull();
        assertThat(response.getActivatedAt()).isNotNull();
    }

    @Test
    public void test2() {
        //given
        var user = saveUser("신유섭", "1234", "tes@spa.com", UserRole.USER);
        var post = postRepository.save(Post.builder()
                .title("title test2")
                        .user(user)
                .content("test2")
                .build()
        );
        //when
        var response = postService.getPost(post.getId());
        //then
        assertThat(response.getTitle()).isEqualTo("title test2");
        assertThat(response.getContent()).isEqualTo("test2");
        assertThat(response.getId()).isEqualTo(post.getId());
        assertThat(response.getCreatedAt()).isBetween(post.getCreatedAt().minusSeconds(1), post.getCreatedAt());
        assertThat(response.getActivatedAt()).isBetween(post.getActivatedAt().minusSeconds(1), post.getActivatedAt());
    }

    @DisplayName("존재하지 않는 게시글 조회시 예외가 발생한다.")
    @Test
    void test3() {
        //given
        Long id = 100L;
        //when //then
        assertThatThrownBy(() -> postService.getPost(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("선택한 게시글은 존재하지 않습니다.");
    }
}