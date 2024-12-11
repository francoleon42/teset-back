package com.teset.service;

import com.teset.dto.login.*;

import java.util.List;

public interface IAuthService {
    LoginPasoUnoResponseDTO loginPasoUno(LoginRequestDTO userDto);
    LoginResponseDTO loginPasoDos(CodigoVerificationRequestDTO requestDto);


    LoginResponseDTO register(RegisterRequestDTO userToRegisterDto);
    void logout(String token);
    void update(Integer id, UpdateRequestDTO userToUpdateDto);
    void habilitar(Integer id);
    void inhabilitar(Integer id);
    List<GetUserDTO> getAll();
}
