package com.teset.service;

import com.teset.dto.login.*;
import com.teset.model.Usuario;

import java.util.List;

public interface IAuthService {
    LoginPasoUnoResponseDTO loginStepOne(LoginRequestDTO userDto);
    LoginResponseDTO loginStepTwo(CodigoVerificationRequestDTO requestDto);

    void logout(String token);

    void updateStepOne(Integer id);
    void updateStepTwo(Integer id, UpdatePasswordRequestDTO userToUpdateDto);

    LoginResponseDTO register(RegisterRequestDTO userToRegisterDto);

    void habilitar(Integer id);
    void inhabilitar(Integer id);
    List<GetUserDTO> getAll();
}
