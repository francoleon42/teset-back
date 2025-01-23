package com.teset.dto.auth;

import com.teset.utils.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResponseDTO {
    private String username;
    private Rol role;
}
