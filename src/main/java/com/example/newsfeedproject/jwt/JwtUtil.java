package com.example.newsfeedproject.jwt;

import com.example.newsfeedproject.dto.JwtUser;
import com.example.newsfeedproject.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.*;

@Component
@Slf4j
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_AUTHORIZATION_HEADER = "Refresh-Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String REFRESH_TYPE = "REFRESH";
    public static final String ACCESS_TYPE = "ACCESS";

    private final long accessTokenTime;
    private final long refreshTokenTime;

    private final Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Autowired
    public JwtUtil(
            @Value("${jwt.accessToken.duration}")
            long accessTokenTime,
            @Value("${jwt.refreshToken.duration}")
            long refreshTokenTime,
            @Value("${jwt.secret.key}")
            String secretKey
    ) {
        this.accessTokenTime = accessTokenTime;
        this.refreshTokenTime = refreshTokenTime;
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(JwtUser user, String type) {
        long duration = 0;

        if (type.equals(REFRESH_TYPE)) {
            duration = refreshTokenTime;
        } else if (type.equals(ACCESS_TYPE)) {
            duration = accessTokenTime;
        }

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(user.id().toString())
                .claim("type", type)
                .claim("user", user)
                .setExpiration(new Date(new Date().getTime() + duration)) // 만료 시간
                .setIssuedAt(new Date()) // 발급일
                .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                .compact();
    }

    private String removePrefix(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private Claims getCustomerClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰에 담긴 유저 정보를 매핑하는 함수입니다.
     * 토큰이 유효하지 않거나 로직에서 원하는 타입이 아닌 경우에는 empty를 반환합니다.
     *
     * @param token Bearer jwtToken...
     * @param type  refresh, access 토큰 타입
     * @return 유저 정보가 담긴 Optinal 객체
     */
    public Optional<JwtUser> getCustomerInfoFrom(String token, String type) {
        if (token == null || !token.startsWith("Bearer")) return Optional.empty();

        token = removePrefix(token);
        if (!validateToken(token)) return Optional.empty();

        Claims jwt = getCustomerClaim(token);
        var jwtUser = (JwtUser) jwt.get("user");

        String tokenType = jwt.get("type", String.class);
        if (!tokenType.equals(type)) return Optional.empty();

        return Optional.of(jwtUser);
    }
}