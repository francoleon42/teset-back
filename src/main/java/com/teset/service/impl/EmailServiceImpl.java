package com.teset.service.impl;

import com.teset.service.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    // Obtenido de MailConfiguration
    private final JavaMailSender mailSender;

    @Override
    public void enviarCorreo(String to, String asunto, String text) {
        String emailFrom = System.getenv("EMAIL_SENDER");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(to);
        message.setSubject(asunto);
        message.setText(text);
        mailSender.send(message);
    }
}
