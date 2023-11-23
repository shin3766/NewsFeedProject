package com.example.newsfeedproject.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @DisplayName("Post의 유저는 null이 아니고 유저의 아이디도 null이 아니면 Post에 user를 등록할 수 있다.")
    @Test
    void setUserWhenUserIsNotNullAndMustHaveId() {
        // given
        var user = new User();
        user.setId(100L);

        var post = new Post();
        // when
        post.setUser(user);
        // then
        assertThat(post.getUser().equals(user)).isTrue();
    }
}