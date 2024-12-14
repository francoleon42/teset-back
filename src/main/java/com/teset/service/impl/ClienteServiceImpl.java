package com.teset.service.impl;

import com.teset.service.IClienteService;
import com.teset.utils.enums.EstadoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {


    @Override
    public String getEstadoCliente(String dni) {
        // desarrollar logica web service o bd para verificar si el cliente esta disponible.
        return EstadoCliente.DISPONIBLE.toString();
    }
}
