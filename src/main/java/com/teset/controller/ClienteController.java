package com.teset.controller;

import com.teset.dto.login.GetUserDTO;

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


    //revisar el tipo de dato del dni del webService o BD
    @GetMapping("/get_estado_cliente/{dni}")
    public ResponseEntity<String> getEstadoCliente(@PathVariable String dni) {
        return new ResponseEntity<>(iClienteService.getEstadoCliente(dni), HttpStatus.OK);
    }


}
