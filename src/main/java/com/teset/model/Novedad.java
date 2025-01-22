package com.teset.model;


import com.teset.utils.enums.TipoContacto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "novedad")
public class Novedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "link_imagen")
    private String linkImagen;

    @Column(name = "link_comercio")
    private String linkComercio;

}
