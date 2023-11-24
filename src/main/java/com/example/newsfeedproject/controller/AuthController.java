package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.CreateEmailCodeRequest;
import com.example.newsfeedproject.dto.MessageDto;
import com.example.newsfeedproject.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final MailService mailService;
    @Value("${email.auth.subject}")
    private String EMAIL_AUTH_SUBJECT;

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
}
