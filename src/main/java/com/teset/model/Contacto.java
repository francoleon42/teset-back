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
@Table(name = "contacto")
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "subTitulo")
    private String subTitulo;


    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoContacto tipo;

    @Column(name = "link")
    private String logoLink;

}
