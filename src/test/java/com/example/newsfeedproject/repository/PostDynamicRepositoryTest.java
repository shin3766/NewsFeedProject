package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.IntegrationTest;
import com.example.newsfeedproject.dto.PageDto;
import com.example.newsfeedproject.dto.PostListItemDto;
import com.example.newsfeedproject.dto.PostSearchConditionParam;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostDynamicRepositoryTest extends IntegrationTest {

    @DisplayName("게시글 목록 조회")
    @Test
    void findListByCondition() {
        // given
        User user = saveUser("john", "1234", "spa@gmail.com", UserRole.USER);
        for (int i = 0; i < 20; i++) savePost("hello" + i, "content" + i, user);
        var condition = new PostSearchConditionParam(5, 1, "content1");
        // when
        PageDto result = postDynamicRepository.findListByCondition(condition);
        // then
        assertThat(result.currentPage()).isEqualTo(1);
        assertThat(result.size()).isEqualTo(5);
        assertThat(result.totalElement()).isEqualTo(11);
        assertThat(result.totalPage()).isEqualTo(3);
        assertThat(result.data()).map(row -> ((PostListItemDto) row).title())
                .contains("hello14", "hello15", "hello16", "hello17", "hello18");
    }
}