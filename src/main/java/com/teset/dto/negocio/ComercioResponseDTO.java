package com.teset.dto.negocio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComercioResponseDTO {
    Long id;
    String codCom;
    String nombre;
    String rubro;
    String localidad;
    String direccion;
    String telefono;
    String logoLink;
    String urlpagweb;
    String Urlgmaps;
}
