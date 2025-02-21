package com.teset.service;

import com.teset.utils.enums.PropositoCode;

public interface IEmailService {
    void generarCorreo(String destino, PropositoCode proposito, String codigo) ;
}
