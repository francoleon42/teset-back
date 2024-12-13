package com.teset.controller;

import com.teset.dto.login.*;
import com.teset.exception.BadRoleException;
import com.teset.exception.NotFoundException;
import com.teset.model.Usuario;
import com.teset.service.IAuthService;
import com.teset.utils.enums.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/login/step-one")
    public ResponseEntity<?> loginPasoUno(@RequestBody LoginRequestDTO loginRequestDto) {
        return ResponseEntity.ok(authService.loginStepOne(loginRequestDto));
    }

    @PostMapping("/login/step-two")
    public ResponseEntity<?> loginPasoDos(@RequestBody CodigoVerificationRequestDTO codigoVerificationRequestDTO) {
        return ResponseEntity.ok(authService.loginStepTwo(codigoVerificationRequestDTO));
    }


    @PatchMapping("/updateStepOne")
    public ResponseEntity<?> loginPasoUno() {
        Usuario user = getUserFromToken();
        authService.updateStepOne(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/updateStepTwo")
    public ResponseEntity<?> update(@RequestBody UpdatePasswordRequestDTO updateRequestDto) {
        Integer idUser = getUserFromToken().getId();
        authService.updateStepTwo(idUser, updateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDto) {
        return new ResponseEntity<>(authService.register(registerRequestDto), HttpStatus.CREATED);
    }

    @PatchMapping("/inhabilitar/{id}")
    public ResponseEntity<?> inhabilitar(@PathVariable Integer id) {
        authService.inhabilitar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/habilitar/{id}")
    public ResponseEntity<?> habilitar(@PathVariable Integer id) {
        authService.habilitar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<GetUserDTO>> getAll() {
        return new ResponseEntity<>(authService.getAll(), HttpStatus.OK);
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
