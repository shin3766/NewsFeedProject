package com.example.newsfeedproject.entity;

import com.example.newsfeedproject.dto.profiledto.ProfileRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped{
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
    private UserRoleEnum role;

    public User(String username, String password, String email, UserRoleEnum role, String intro) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.intro = intro;
    }

    public void update(ProfileRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.intro = requestDto.getIntro();
        this.email = requestDto.getEmail();
    }

    public Long getId(){
        return id;
    }

    /*
    다른 테이블에서 유저의 기본키를 외래키로 사용하여 관계를 설정하기 위해서 사용하는 함수
     */
    public static User foreign(Long id) {
        var user = new User();
        user.id = id;
        return user;
    }
}