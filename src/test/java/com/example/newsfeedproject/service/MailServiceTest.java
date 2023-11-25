package com.example.newsfeedproject.service;

import com.example.newsfeedproject.IntegrationTest;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("메일 서비스 테스트")
class MailServiceTest extends IntegrationTest {

    @Autowired
    MailService mailService;
    @Autowired
    UserStatusService userStatusService;

    @DisplayName("이메일 인증 코드 전송시에는 유저 상태 서비스에서 이메일로 코드를 검증할 수 있다.")
    @Test
    void sendEmail() throws MessagingException {
        // given
        String to = "youdong98@naver.com";
        String subject = "test 입니다.";
        String code = "testcode";
        // when
        mailService.sendEmailAuthCode(to, subject, code);
        // then
        assertThat(userStatusService.matchesEmailAuthCode(to, code)).isTrue();
    }

    @DisplayName("인증 코드 생성시 8자리 이다.")
    @Test
    void createAuthCode() {
        // when
        String code = mailService.createEmailAuthCode();
        // then
        assertThat(code).hasSize(8);
    }

    @DisplayName("인증 코드는 생성할 때 마다 다르다.")
    @Test
    void createAuthCodeDiff() {
        // when
        int size = 100;
        var set = new HashSet<String>();
        for(int i = 0; i < size; i++){
            set.add(mailService.createEmailAuthCode());
        }
        // then
        assertThat(set).hasSize(size);
    }
}