package com.teset.dto.negocio;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NovedadResponseDTO {

    Integer id;
    String titulo;
    String linkImagen;
    String linkComercio;
    boolean mostrarEnInicio;
}
