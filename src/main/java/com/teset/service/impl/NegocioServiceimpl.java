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
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NegocioServiceimpl implements INegocioService {

    private final IWebServiceTesetClient iWebServiceTesetClient;
    private final INovedadRepository novedadRepository;
    private final IContactoRepository contactoRepository;

    private volatile List<ComercioResponseDTO> cachedComercios;
    private long cacheExpirationTime;

    @Override
    public List<ComercioResponseDTO> getComerciosAdheridos() {
        List<ComercioResponseDTO> comercios = iWebServiceTesetClient.getComerciosAdheridosTeset();
        return comercios.stream()
                .limit(100)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComercioResponseDTO> getComerciosAdheridosPorNombre(String nombre) {
        if (cachedComercios == null || System.currentTimeMillis() > cacheExpirationTime) {
            cachedComercios = iWebServiceTesetClient.getComerciosAdheridosTeset();
            cacheExpirationTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
        }
        List<ComercioResponseDTO> comerciosByName = cachedComercios;

        if (nombre == null || nombre.trim().isEmpty()) {
            return comerciosByName.stream()
                    .limit(100)
                    .collect(Collectors.toList());
        }

        String nombreBusqueda = nombre.toLowerCase().trim();
        JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();

        return comerciosByName.parallelStream()
                .filter(comercio -> comercio.getNombre() != null)
                .map(comercio -> {
                    String lowerNombre = comercio.getNombre().toLowerCase();
                    double score = similarity.apply(lowerNombre, nombreBusqueda);
                    return new AbstractMap.SimpleEntry<>(comercio, score);
                })
                .filter(entry -> entry.getValue() > 0.7)
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
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
                .mostrarEnInicio(novedad.isMostrarEnInicio())
                .build();
    }
    private ContactoResponseDTO convertToContactoDTO(Contacto contacto) {
        return ContactoResponseDTO.builder()
                .id(contacto.getId())
                .titulo(contacto.getTitulo())
                .subTitulo(contacto.getSubTitulo())
                .tipo(contacto.getTipo().toString())
                .logoLink(contacto.getLogoLink())
                .link(contacto.getLink())
                .build();

    }
}
