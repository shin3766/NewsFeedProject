package com.example.newsfeedproject.service;

import com.example.newsfeedproject.entity.User;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 로그인 유저의 상태를 조회하는 매니저 클래스
 */
@Component
public class UserStatusService {

    private final ConcurrentMap<String, String> redis = new ConcurrentHashMap<>();

    /**
     * todo
     * JWT 토큰을 추출해서 유저의 정보를 반환한다.
     *
     * @return
     */
    public User getLoginUser() {
        User user = new User();
        user.setId(100L);
        return user;
    }

    public void saveEmailAuthCode(String email, String code) {
        redis.put(email, code);
    }

    public boolean matchesEmailAuthCode(String email, String code) {
        if (!redis.containsKey(email)) return false;
        return redis.get(email).equals(code);
    }
}
