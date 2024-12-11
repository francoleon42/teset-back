package com.teset.dto.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodigoVerificationRequestDTO {
    Integer codigo;
    String username;
}
