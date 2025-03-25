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
        return iWebServiceTesetClient.getDetallesClienteTeset(dni).stream()
                .map(this::convertirDetalleSegunimportePxVto)
                .toList();
    }

    private DetalleResponseDTO convertirDetalleSegunimportePxVto(DetalleResponseDTO detalleResponseDTO){
        if (detalleResponseDTO.getImportePxVto() == 0.0){
            return DetalleResponseDTO.builder()
                    .id(detalleResponseDTO.getId())
                    .secuencia(detalleResponseDTO.getSecuencia())
                    .codCom(detalleResponseDTO.getCodCom())
                    .importe(detalleResponseDTO.getImporte())
                    .fechaEmision(detalleResponseDTO.getFechaEmision())
                    .build();
        }
        return DetalleResponseDTO.builder()
                .id(detalleResponseDTO.getId())
                .codCom(detalleResponseDTO.getCodCom())
                .fechaEmision(detalleResponseDTO.getFechaEmision())
                .fechadeProximoVencimiento(detalleResponseDTO.getFechadeProximoVencimiento())
                .secuencia(detalleResponseDTO.getSecuencia())
                .importe(detalleResponseDTO.getImporte())
                .cuota(detalleResponseDTO.getCuota())
                .secuencia(detalleResponseDTO.getSecuencia())
                .codCom(detalleResponseDTO.getCodCom())
                .importe(detalleResponseDTO.getImporte())
                .importePxVto(detalleResponseDTO.getImportePxVto())

                .build();
    }


}
