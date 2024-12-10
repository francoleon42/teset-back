package com.teset.service;

import com.teset.dto.chofer.ChoferRegistroDTO;
import com.teset.dto.chofer.ChoferResponseDTO;

import java.util.List;

public interface IChoferService {
    void registro(ChoferRegistroDTO choferRegistroDTO);

    void inhabilitarUsuarioChofer(Integer idUsuario);
    List<ChoferResponseDTO> obtenerAll();

}
