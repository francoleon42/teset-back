package com.teset.dto.auth;

import com.teset.utils.enums.Rol;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateResponseDTO {
    private String username;
    private Rol role;
}
