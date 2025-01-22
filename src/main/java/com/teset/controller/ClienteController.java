package com.teset.controller;

import com.teset.dto.cliente.ClienteResponseDTO;
import com.teset.dto.cliente.DetalleResponseDTO;
import com.teset.service.IClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final IClienteService iClienteService;


    @GetMapping("/{dni}")
    public ResponseEntity<ClienteResponseDTO> getCliente(@PathVariable String dni) {
        return new ResponseEntity<>(iClienteService.getCliente(dni), HttpStatus.OK);
    }

    @GetMapping("/detalle/{dni}")
    public ResponseEntity<DetalleResponseDTO> getDetalleCliente(@PathVariable String dni) {
        return new ResponseEntity<>(iClienteService.getDetalleCliente(dni), HttpStatus.OK);
    }


}
