package com.example.newsfeedproject.entity;

import com.example.newsfeedproject.dto.profiledto.ProfileRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "users") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class ProfileUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    public ProfileUser(ProfileRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.email = requestDto.getEmail();
    }

    public void update(ProfileRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.email = requestDto.getEmail();
    }

    public Long getId(){
        return id;
    }
}
