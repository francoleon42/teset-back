package com.teset.service;

import com.teset.dto.cliente.ClienteResponseDTO;
import com.teset.dto.cliente.DetalleResponseDTO;

import java.util.List;

public interface IClienteService {
     ClienteResponseDTO getCliente(String dni);
     List<DetalleResponseDTO> getDetalleCliente(String dni);
}
