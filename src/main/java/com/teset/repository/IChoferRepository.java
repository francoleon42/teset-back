package com.teset.repository;

import com.teset.model.Chofer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IChoferRepository extends JpaRepository<Chofer, Integer> {
    Optional<Chofer> findByUsuario_Id(Integer usuarioId);

}
