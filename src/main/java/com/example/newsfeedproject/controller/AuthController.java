package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.CreateEmailCodeRequest;
import com.example.newsfeedproject.dto.MessageDto;
import com.example.newsfeedproject.service.MailService;
import com.example.newsfeedproject.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final MailService mailService;
    @Value("${email.auth.subject}")
    private String EMAIL_AUTH_SUBJECT;
    private final UserService userService;

    @PostMapping("/signup/email")
    public ResponseEntity<?> requestEmailCode(@RequestBody CreateEmailCodeRequest request) {
        try {
            String code = mailService.createEmailAuthCode();
            mailService.sendEmailAuthCode(request.email(), EMAIL_AUTH_SUBJECT, code);
            return ResponseEntity.ok(new MessageDto(request.email() + "을 확인하세요."));
        } catch (RuntimeException | MessagingException ex) {
            return ResponseEntity.badRequest().body(new MessageDto("입력을 확인해주세요."));
        }
    }

    @DeleteMapping("/signout")
    public ResponseEntity<?> signOut(){
        userService.signOut();
        return ResponseEntity.ok(new MessageDto("탈퇴했습니다."));
    }
}
