package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.postDto.PostRequestDto;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;

    @Test
    void test1(){
        // given
        var request = new PostRequestDto("title", "content");

        // when
        var response = postService.createPost(request);

        // then
        Assertions.assertThat(response.getTitle()).isEqualTo("title");
        Assertions.assertThat(response.getContent()).isEqualTo("content");
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getCreatedAt()).isNotNull();
        Assertions.assertThat(response.getActivatedAt()).isNotNull();
    }

    @Test
    public void test2() throws Exception {
        //given
        var post = postRepository.save(Post.builder()
                .title("title test2")
                .content("test2")
                        .build()
        );
        //when
        var response = postService.getPost(post.getId());
        //then
        Assertions.assertThat(response.getTitle()).isEqualTo("title test2");
        Assertions.assertThat(response.getContent()).isEqualTo("test2");
        Assertions.assertThat(response.getId()).isEqualTo(post.getId());
        Assertions.assertThat(response.getCreatedAt()).isBetween(post.getCreatedAt().minusSeconds(1), post.getCreatedAt());
        Assertions.assertThat(response.getActivatedAt()).isBetween(post.getActivatedAt().minusSeconds(1), post.getActivatedAt());
    }

    @DisplayName("존재하지 않는 게시글 조회시 예외가 발생한다.")
    @Test
    void test3(){
        //given
        Long id = 100L;
        //when //then
        Assertions.assertThatThrownBy(() -> postService.getPost(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("선택한 게시글은 존재하지 않습니다.");
    }
}