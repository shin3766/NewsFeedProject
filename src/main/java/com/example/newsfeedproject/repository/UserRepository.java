package com.example.newsfeedproject.repository;

import com.example.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {     //spring data jpa
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
