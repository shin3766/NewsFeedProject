package com.example.newsfeedproject.entity;

import com.example.newsfeedproject.dto.postDto.PostRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime activatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user = new User();

    @Builder
    private Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.createdAt = LocalDateTime.now();
    }
    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    // User 담당과 이야기 필요
//    public void setUser(User user) {
//        this.user = user;
//        user.getPostList().add(this);
//    }
}
