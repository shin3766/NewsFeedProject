package com.example.newsfeedproject.manager;

import com.example.newsfeedproject.entity.User;
import org.springframework.stereotype.Component;

/**
 * 로그인 유저의 상태를 조회하는 매니저 클래스
 */
@Component
public class UserStatusManager {

    /**
     * JWT 토큰을 추출해서 유저의 정보를 반환한다.
     * @return
     */
    public User getLoginUser(){
        User user = new User();
        user.setId(100L);
        return user;
    }
}
