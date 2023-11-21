package com.example.newsfeedproject.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (JwtException ex) {
//            String message = ex.getMessage();
//            if(ErrorCode.UNKNOWN_ERROR.getMessage().equals(message)) {
//                setResponse(response, ErrorCode.UNKNOWN_ERROR);
//            }
//            //잘못된 타입의 토큰인 경우
//            else if(ErrorCode.WRONG_TYPE_TOKEN.getMessage().equals(message)) {
//                setResponse(response, ErrorCode.WRONG_TYPE_TOKEN);
//            }
//            //토큰 만료된 경우
//            else if(ErrorCode.EXPIRED_TOKEN.getMessage().equals(message)) {
//                setResponse(response, ErrorCode.EXPIRED_TOKEN);
//            }
//            //지원되지 않는 토큰인 경우
//            else if(ErrorCode.UNSUPPORTED_TOKEN.getMessage().equals(message)) {
//                setResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
//            }
//            else {
//                setResponse(response, ErrorCode.ACCESS_DENIED);
//            }
        }
    }

//    private void setResponse(HttpServletResponse response, ErrorCode errorMessage) throws RuntimeException, IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(errorMessage.getStatus().value());
//        response.getWriter().print(errorMessage.getMessage());
//    }
}