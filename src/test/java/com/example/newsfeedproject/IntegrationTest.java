package com.example.newsfeedproject;

import com.example.newsfeedproject.entity.Comment;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserRoleEnum;
import com.example.newsfeedproject.repository.CommentRepository;
import com.example.newsfeedproject.repository.PostDynamicRepository;
import com.example.newsfeedproject.repository.PostRepository;
import com.example.newsfeedproject.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected PostDynamicRepository postDynamicRepository;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected UserRepository userRepository;

    protected Post savePost(String title, String content) {
        return postRepository.saveAndFlush(Post.builder()
                .title(title)
                .content(content)
                .build()
        );
    }

    protected User saveUser(String username, String password, String email, UserRoleEnum role) {
        User user = new User(username, password, email, role);
        return userRepository.saveAndFlush(user);
    }

    protected Comment saveComment(String content, User user) {
        return commentRepository.saveAndFlush(Comment.builder()
                .content(content)
                .user(user)
                .build()
        );
    }
}
