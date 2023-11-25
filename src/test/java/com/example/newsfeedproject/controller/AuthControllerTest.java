package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.IntegrationTest;
import com.example.newsfeedproject.dto.CreateEmailCodeRequest;
import com.example.newsfeedproject.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;

import static com.example.newsfeedproject.entity.UserRole.USER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 테스트를 위한 메일서버를 가동해야 테스트를 실행할 수 있습니다.
 * compose.yml를 통해서 메일 서버를 가동해주세요.
 */
@DisplayName("인증 API 테스트")
class AuthControllerTest extends IntegrationTest {

    @DisplayName("이메일 인증 코드 발급 성공")
    @Test
    void makeEmailCode() throws Exception {
        // given
        var request = new CreateEmailCodeRequest("test@spa.com");
        // when // then
        mockMvc.perform(post(("/api/v1/signup/email"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.message").value("test@spa.com을 확인하세요.")
                )
        ;
    }

    @DisplayName("회원 탈퇴 성공")
    @Test
    void signOutSuccess() throws Exception {
        // given
        User user = saveUser("한정석", "1234", "test@gmail.com", USER);
        SecurityContext context = contextJwtUser(user.getId(), user.getUsername(), user.getRole());
        // when // then
        mockMvc.perform(delete("/api/v1/signout")
                        .with(securityContext(context))
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.message").value("탈퇴했습니다.")
                );
    }

    @DisplayName("로그인하지 않은 유저라면 탈퇴할 수 없다.")
    @Test
    void signOutFailWhenNotLogin() throws Exception {
        // when // then
        mockMvc.perform(delete("/api/v1/signout")
                )
                .andDo(print())
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("$.message").value("권한이 없습니다.")
                );
    }
}