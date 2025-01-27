package com.teset.dto.negocio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComercioResponseDTO {
    String id;
    String nombre;
    String direccion;
    String telefono;
    String logoLink;
    String link;
}
