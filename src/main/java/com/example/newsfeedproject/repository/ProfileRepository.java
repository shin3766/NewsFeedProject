package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.ProfileUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileUser, Long> {
    // ID에 해당하는 프로필을 찾아서 반환
    Optional<ProfileUser> findById(Long id);
}