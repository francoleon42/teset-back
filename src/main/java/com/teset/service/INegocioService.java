package com.teset.service;

import com.teset.dto.negocio.ComercioResponseDTO;
import com.teset.dto.negocio.ContactoResponseDTO;
import com.teset.dto.negocio.NovedadResponseDTO;

import java.util.List;

public interface INegocioService {

    List<ComercioResponseDTO> getComerciosAdheridos();
    List<ComercioResponseDTO> getComerciosAdheridosPorNombre(String nombre);
    List<ContactoResponseDTO> getContactos();
    List<NovedadResponseDTO> getNovedades();
}
