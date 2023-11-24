package com.example.newsfeedproject.jwt;

import com.example.newsfeedproject.dto.JwtAuthentication;
import com.example.newsfeedproject.dto.JwtUser;
import com.example.newsfeedproject.security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.newsfeedproject.jwt.JwtUtil.ACCESS_TYPE;
import static com.example.newsfeedproject.jwt.JwtUtil.AUTHORIZATION_HEADER;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String token = req.getHeader(AUTHORIZATION_HEADER);

        Optional<JwtUser> bearerToken = jwtUtil.getCustomerInfoFrom(token, ACCESS_TYPE);

        // 유효한 엑세스 토큰인 경우 인가 처리
        bearerToken.ifPresent(this::setAuthentication);
        filterChain.doFilter(req, res);
    }

    public void setAuthentication(JwtUser user) {
        var authorities = List.of(new SimpleGrantedAuthority(user.role().name()));
        var authentication = new JwtAuthentication(user, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}