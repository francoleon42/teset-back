package com.teset.dto.auth;

import com.teset.utils.enums.Rol;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String username;
    private String token;
    private Rol role;

}

