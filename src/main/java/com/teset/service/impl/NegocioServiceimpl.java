package com.teset.service.impl;


import com.teset.dto.negocio.ComercioResponseDTO;
import com.teset.dto.negocio.ContactoResponseDTO;
import com.teset.dto.negocio.NovedadResponseDTO;
import com.teset.model.Contacto;
import com.teset.model.Novedad;
import com.teset.repository.IContactoRepository;
import com.teset.repository.INovedadRepository;
import com.teset.service.INegocioService;
import com.teset.webService.IWebServiceTesetClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NegocioServiceimpl implements INegocioService {

    private final IWebServiceTesetClient iWebServiceTesetClient;
    private final INovedadRepository novedadRepository;
    private final IContactoRepository contactoRepository;

    @Override
    public List<ComercioResponseDTO> getComerciosAdheridos() {
        return iWebServiceTesetClient.getComerciosAdheridosTeset();
    }

    @Override
    public List<ContactoResponseDTO> getContactos() {
        return contactoRepository.findAll().stream()
                .map(this::convertToContactoDTO)
                .toList();
    }

    @Override
    public List<NovedadResponseDTO> getNovedades() {
        return novedadRepository.findAll().stream()
                .map(this::convertToNovedadDTO)
                .toList();
    }

    private NovedadResponseDTO convertToNovedadDTO(Novedad novedad) {
        return NovedadResponseDTO.builder()
                .id(novedad.getId())
                .titulo(novedad.getTitulo())
                .linkImagen(novedad.getLinkImagen())
                .linkComercio(novedad.getLinkComercio())
                .build();
    }
    private ContactoResponseDTO convertToContactoDTO(Contacto contacto) {
        return ContactoResponseDTO.builder()
                .id(contacto.getId())
                .titulo(contacto.getTitulo())
                .subTitulo(contacto.getSubTitulo())
                .tipo(contacto.getTipo().toString())
                .logoLink(contacto.getLogoLink())
                .build();

    }
}
