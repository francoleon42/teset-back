package com.teset.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginPasoUnoResponseDTO {

    private String username;
    private boolean nuevoDispositivo;
}
