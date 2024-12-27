package com.teset.dto.auth;

import com.teset.utils.enums.Rol;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetUserDTO {
    private Integer id;
    private String username;
    private Rol role;
    private String estado;
}
