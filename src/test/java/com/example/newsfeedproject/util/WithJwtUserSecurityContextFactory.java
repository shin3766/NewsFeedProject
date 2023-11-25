package com.example.newsfeedproject.util;

import com.example.newsfeedproject.dto.JwtAuthentication;
import com.example.newsfeedproject.dto.JwtUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

import static com.example.newsfeedproject.entity.UserRole.USER;

public class WithJwtUserSecurityContextFactory implements WithSecurityContextFactory<WithJwtUser> {
    @Override
    public SecurityContext createSecurityContext(WithJwtUser config) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        var jwtUser = new JwtUser(config.id(), config.username(), USER);
        var auth = new JwtAuthentication(jwtUser, List.of(new SimpleGrantedAuthority(jwtUser.role().name())));
        context.setAuthentication(auth);
        return context;
    }
}
