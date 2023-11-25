package com.example.newsfeedproject;

import com.example.newsfeedproject.dto.JwtAuthentication;
import com.example.newsfeedproject.dto.JwtUser;
import com.example.newsfeedproject.entity.Comment;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserRole;
import com.example.newsfeedproject.jwt.JwtUtil;
import com.example.newsfeedproject.repository.CommentRepository;
import com.example.newsfeedproject.repository.PostDynamicRepository;
import com.example.newsfeedproject.repository.PostRepository;
import com.example.newsfeedproject.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeedproject.entity.UserRole.USER;

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
    @Autowired
    protected JwtUtil jwtUtil;

    protected Post savePost(String title, String content, User user) {
        return postRepository.saveAndFlush(Post.builder()
                .title(title)
                .user(user)
                .content(content)
                .build()
        );
    }

    protected User saveUser(String username, String password, String email, UserRole role) {
        User user = new User(username, password, email, role, "intro");
        return userRepository.saveAndFlush(user);
    }

    protected Comment saveComment(String content, User user, Post post) {
        return commentRepository.saveAndFlush(Comment.builder()
                .content(content)
                .post(post)
                .user(user)
                .build()
        );
    }

    protected SecurityContext contextJwtUser(Long id, String username, UserRole role){
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        var jwtUser = new JwtUser(id, username, role);
        var auth = new JwtAuthentication(jwtUser, List.of(new SimpleGrantedAuthority(jwtUser.role().name())));
        context.setAuthentication(auth);
        return context;
    }
}
