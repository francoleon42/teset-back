package com.teset.service;

public interface IEmailService {
    void enviarCorreo(String toUser, String asunto, String text);
}
