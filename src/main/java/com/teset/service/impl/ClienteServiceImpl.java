package com.teset.service.impl;

import com.teset.dto.cliente.GetClienteResponseDTO;
import com.teset.service.IClienteService;
import com.teset.utils.enums.EstadoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {


    @Override
    public GetClienteResponseDTO getCliente(String dni) {
        // desarrollar logica para obetener del web service la informacion del cliente si existe
        // simulo respuesta de web service
        return GetClienteResponseDTO
                .builder()
                .dni("44382619")
                .email("francoleon2001@gmail.com")
                .estado(EstadoCliente.DISPONIBLE)
                .build();
    }
}
