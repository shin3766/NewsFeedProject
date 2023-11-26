package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "user")
    Optional<Post> findFetchJoinUserById(Long id);
}
