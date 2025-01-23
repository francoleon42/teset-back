package com.teset.controller;

import com.teset.dto.auth.*;
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


    @PatchMapping("/update/step-one/{dni}")
    public ResponseEntity<?> updateStepOne(@PathVariable String dni) {
        authService.updateStepOne(dni);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/step-two")
    public ResponseEntity<?> updateStepTwo(@RequestBody UpdatePasswordRequestDTO updateRequestDto) {
        authService.updateStepTwo( updateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register/step-one")
    public ResponseEntity<?> registerStepOne(@RequestBody RegisterRequestDTO registerRequestDto) {
        return new ResponseEntity<>(authService.registerStepOne(registerRequestDto), HttpStatus.CREATED);
    }
    @PatchMapping("/register/step-two")
    public ResponseEntity<?> registerStepTwo(@RequestBody RegisterTwoRequestDTO registerTwoRequestDto) {
        return new ResponseEntity<>(authService.registerStepTwo(registerTwoRequestDto), HttpStatus.OK);
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



}
