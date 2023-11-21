package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
