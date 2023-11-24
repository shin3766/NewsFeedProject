package com.example.newsfeedproject.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final UserStatusService userStatusManager;
    private final SpringTemplateEngine templateEngine;

    public String createEmailAuthCode(){
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void sendEmailAuthCode(String toEmail, String subject, String code) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        message.setFrom("noreply@gmail.com");
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail); //보낼 이메일 설정
        message.setSubject(subject); //제목 설정
        message.setText(createEmailAuthHtml(code), "UTF-8", "html");

        userStatusManager.saveEmailAuthCode(toEmail, code);
        emailSender.send(message);
    }

    private String createEmailAuthHtml(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("emailAuthCode", context);
    }
}
