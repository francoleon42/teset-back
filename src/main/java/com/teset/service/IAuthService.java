package com.teset.service;

import com.teset.dto.auth.*;

import java.util.List;

public interface IAuthService {
    LoginPasoUnoResponseDTO loginStepOne(LoginRequestDTO userDto);
    LoginResponseDTO loginStepTwo(CodigoVerificationRequestDTO requestDto);

    void logout(String token);

    void updateStepOne(String dni);
    void updateStepTwo(UpdatePasswordRequestDTO userToUpdateDto);

    LoginResponseDTO registerStepOne(RegisterRequestDTO userToRegisterDto);
    LoginResponseDTO registerStepTwo(RegisterTwoRequestDTO userRegisterTwo);


    List<GetUserDTO> getAll();
}
