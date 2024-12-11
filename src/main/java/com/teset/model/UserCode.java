package com.teset.model;

import com.teset.utils.enums.EstadoUsuario;
import com.teset.utils.enums.PropositoCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_code")
public class UserCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "creacion")
    private LocalDateTime creacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "proposito_code")
    private PropositoCode propositoCode;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
