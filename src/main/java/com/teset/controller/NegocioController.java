package com.teset.controller;

import com.teset.dto.cliente.ClienteResponseDTO;
import com.teset.dto.negocio.ComercioResponseDTO;
import com.teset.dto.negocio.ContactoResponseDTO;
import com.teset.dto.negocio.NovedadResponseDTO;
import com.teset.service.INegocioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/negocio")
@RequiredArgsConstructor
public class NegocioController {

    private final INegocioService negocioService;


    @GetMapping("/comercios_adheridos")
    public ResponseEntity<List<ComercioResponseDTO>> getcomerciosAdheridos() {
        return new ResponseEntity<>(negocioService.getComerciosAdheridos(), HttpStatus.OK);
    }
    @GetMapping("/contactos")
    public ResponseEntity<List<ContactoResponseDTO>> getContactos() {
        return new ResponseEntity<>(negocioService.getContactos(), HttpStatus.OK);
    }
    @GetMapping("/novedades")
    public ResponseEntity<List<NovedadResponseDTO>> getNovedades() {
        return new ResponseEntity<>(negocioService.getNovedades(), HttpStatus.OK);
    }
}
