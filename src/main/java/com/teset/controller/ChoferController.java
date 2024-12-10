package com.teset.controller;

import com.teset.dto.chofer.ChoferRegistroDTO;
import com.teset.exception.BadRoleException;
import com.teset.exception.NotFoundException;
import com.teset.model.Usuario;
import com.teset.service.IChoferService;
import com.teset.utils.enums.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chofer")
@RequiredArgsConstructor

public class ChoferController {
    private final IChoferService choferService;

    @PostMapping("/registrar")
    public ResponseEntity<?> createChofer(@RequestBody ChoferRegistroDTO choferRegistroDTO) {
        choferService.registro(choferRegistroDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/verChoferes")
    public ResponseEntity<?> getAllChofers() {
        return new ResponseEntity<>(choferService.obtenerAll(), HttpStatus.OK);
    }



//    @GetMapping("/verVehiculo")
//    public ResponseEntity<?> getVerVehiculo() {
//        Usuario u = getUserFromToken();
////        return new ResponseEntity<>(choferService.obtenerVehiculo(u.getId()), HttpStatus.OK);
//    }


    // metodo para obtener el usuario logueado
    private Usuario getUserFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication != null && authentication.isAuthenticated())) {
            throw new NotFoundException("El token no corresponde a un usuario.");
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();
        if (usuario.getRol() != Rol.CHOFER) {
            throw new BadRoleException("El usuario no corresponde a un chofer.");
        }

        return usuario;
    }
}
