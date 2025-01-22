package com.teset.dto.negocio;


import com.teset.utils.enums.TipoContacto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactoResponseDTO {

    Integer id;
    String titulo;
    String subTitulo;
    String tipo;
    String logoLink;

}
