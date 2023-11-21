package com.example.newsfeedproject;

import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.repository.PostDynamicRepository;
import com.example.newsfeedproject.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected PostDynamicRepository postDynamicRepository;

    protected Post savePost(String title, String content) {
        return postRepository.saveAndFlush(Post.builder()
                .title(title)
                .content(content)
                .build());
    }
}
