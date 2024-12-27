package com.teset.service;

import com.teset.dto.cliente.GetClienteResponseDTO;

public interface IClienteService {
     GetClienteResponseDTO getCliente(String dni);
}
