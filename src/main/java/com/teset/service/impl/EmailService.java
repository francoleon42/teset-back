package com.teset.service.impl;

import com.teset.service.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;


    @Override
    public void enviarCorreo(String to, String asunto, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(asunto);
        message.setText(body);
        javaMailSender.send(message);
    }
}
