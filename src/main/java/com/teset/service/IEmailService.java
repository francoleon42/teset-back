package com.teset.service;

public interface IEmailService {
    void enviarCorreo(String to, String asunto, String body);
}
