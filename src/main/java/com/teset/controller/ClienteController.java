package com.teset.controller;

import com.teset.dto.cliente.ClienteResponseDTO;
import com.teset.dto.cliente.DetalleResponseDTO;
import com.teset.exception.BadRoleException;
import com.teset.exception.NotFoundException;
import com.teset.model.Usuario;
import com.teset.service.IClienteService;
import com.teset.utils.enums.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final IClienteService iClienteService;


    @GetMapping("/info")
    public ResponseEntity<ClienteResponseDTO> getCliente() {
        Usuario usuario = getUserFromToken();
        return new ResponseEntity<>(iClienteService.getCliente(usuario.getDni()), HttpStatus.OK);
    }

    @GetMapping("/detalle")
    public ResponseEntity<List<DetalleResponseDTO>> getDetalleCliente() {
        Usuario usuario = getUserFromToken();
        return new ResponseEntity<>(iClienteService.getDetalleCliente(usuario.getDni()), HttpStatus.OK);
    }

    private Usuario getUserFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication != null && authentication.isAuthenticated())) {
            throw new NotFoundException("El token no corresponde a un usuario.");
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();
        if (usuario.getRol() != Rol.CLIENTE) {
            throw new BadRoleException("El usuario no corresponde a un cliente.");
        }

        return usuario;
    }
}
