package com.example.newsfeedproject.entity;

import com.example.newsfeedproject.dto.JwtUser;
import com.example.newsfeedproject.dto.profiledto.ProfileRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USERS")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String intro;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String username, String password, String email, UserRole role, String intro) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.intro = intro;
    }

    public void update(ProfileRequestDto req) {
        if(req.getUsername() != null) this.username = req.getUsername();
        if(req.getIntro() != null) this.intro = req.getIntro();
        if(req.getEmail() != null) this.email = req.getEmail();
    }

    /*
    다른 테이블에서 유저의 기본키를 외래키로 사용하여 관계를 설정하기 위해서 사용하는 함수
     */
    public static User foreign(Long id) {
        var user = new User();
        user.id = id;
        return user;
    }

    public static User foreign(JwtUser jwtUser){
        var user = new User();
        user.id = jwtUser.id();
        user.role = jwtUser.role();
        user.username = jwtUser.username();
        return user;
    }
}