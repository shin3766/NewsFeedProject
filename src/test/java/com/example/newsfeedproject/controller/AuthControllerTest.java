package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.IntegrationTest;
import com.example.newsfeedproject.dto.CreateEmailCodeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

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
}