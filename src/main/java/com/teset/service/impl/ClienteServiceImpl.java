package com.teset.service.impl;

import com.teset.dto.cliente.ClienteResponseDTO;
import com.teset.dto.cliente.DetalleResponseDTO;
import com.teset.service.IClienteService;
import com.teset.utils.enums.EstadoCliente;
import com.teset.webService.IWebServiceTesetClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final IWebServiceTesetClient iWebServiceTesetClient;

    @Override
    public ClienteResponseDTO getCliente(String dni) {
        return iWebServiceTesetClient.getClienteTeset(dni);
    }


    @Override
    public DetalleResponseDTO getDetalleCliente(String dni) {
        return iWebServiceTesetClient.getDetalleClienteTeset(dni);
    }


}
