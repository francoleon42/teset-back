package com.teset.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final IWebServiceTesetClient iWebServiceTesetClient;

    @Override
    public ClienteResponseDTO getCliente(String dni) {
        ClienteResponseDTO cliente=  iWebServiceTesetClient.getClienteTeset(dni);
        if(cliente==null){
            return ClienteResponseDTO.builder().estado(EstadoCliente.NO_DISPONIBLE).build();
        }
        return cliente;
    }


    @Override
    public List<DetalleResponseDTO> getDetalleCliente(String dni) {
        return iWebServiceTesetClient.getDetallesClienteTeset(dni);
    }


}
