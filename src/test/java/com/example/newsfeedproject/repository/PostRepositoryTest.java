package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.IntegrationTest;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.entity.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class PostRepositoryTest extends IntegrationTest {

    @DisplayName("게시글 유저 패치 조인 테스트")
    @Test
    void findFetchJoinUserById() throws Exception {
        //given
        var user = saveUser("신유섭", "1234", "tes@spa.com", UserRole.USER);
        var post = savePost("test", "content", user);
        //when
        Optional<Post> findPost = postRepository.findFetchJoinUserById(post.getId());
        //then
        Assertions.assertThat(findPost).isPresent()
                .get().extracting("id", "title", "content", "user.id")
                .containsExactly(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId());
    }
}
