package com.teset.service;

import com.teset.dto.cliente.ClienteResponseDTO;
import com.teset.dto.cliente.DetalleResponseDTO;

public interface IClienteService {
     ClienteResponseDTO getCliente(String dni);
     DetalleResponseDTO getDetalleCliente(String dni);
}
