package com.teset.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodigoVerificationRequestDTO {
    String codigo;
    String username;
    String codDispositivo;
}
