package com.teset.service.impl;


import com.teset.dto.negocio.ComercioResponseDTO;
import com.teset.service.INegocioService;
import com.teset.webService.IWebServiceTesetClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NegocioServiceimpl implements INegocioService {

    private final IWebServiceTesetClient iWebServiceTesetClient;

    @Override
    public List<ComercioResponseDTO> getComerciosAdheridos() {
        return iWebServiceTesetClient.getComerciosAdheridosTeset();
    }
}
