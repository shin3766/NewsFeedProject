package com.example.newsfeedproject.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("로그인 유저 상태 관리 서비스 테스트")
class UserStatusServiceUnitTest {

    private final UserStatusService userStatusService = new UserStatusService();

    @DisplayName("이메일 인증 코드를 저장하고 저장여부를 판별하면 결과는 true이다.")
    @Test
    void emailCodeLogic() {
        // given
        String email = "test@spa.com";
        String code = "EFsd34jk";
        // when
        userStatusService.saveEmailAuthCode(email, code);
        // then
        assertThat(userStatusService.matchesEmailAuthCode(email, code)).isTrue();
    }

    @DisplayName("이메일 인증 코드를 저장하지 않고 저장여부를 판별하면 결과는 false이다.")
    @Test
    void emailCodeLogicFalseWhenNotSave() {
        // given
        String email = "test@spa.com";
        String code = "EFsd34jk";
        // then
        assertThat(userStatusService.matchesEmailAuthCode(email, code)).isFalse();
    }

    @DisplayName("코드가 일치하지 않으면 false")
    @Test
    void emailCodeLogicFalseWhenCodeIsNotMatch() {
        // given
        String email = "test@spa.com";
        String code = "EFsd34jk";
        // when
        userStatusService.saveEmailAuthCode(email, code);
        // then
        assertThat(userStatusService.matchesEmailAuthCode(email, "fdsafasd2")).isFalse();
    }
}