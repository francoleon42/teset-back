package com.teset.controller;

import com.teset.dto.login.GetUserDTO;
import com.teset.dto.login.LoginRequestDTO;
import com.teset.dto.login.RegisterRequestDTO;
import com.teset.dto.login.UpdateRequestDTO;
import com.teset.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDto){
        return new ResponseEntity<>(authService.register(registerRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UpdateRequestDTO updateRequestDto){
        authService.update(id, updateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping("/inhabilitar/{id}")
    public ResponseEntity<?> inhabilitar(@PathVariable Integer id){
        authService.inhabilitar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping("/habilitar/{id}")
    public ResponseEntity<?> habilitar(@PathVariable Integer id){
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
}
