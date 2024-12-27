package com.teset.repository;

import com.teset.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsuario(String usuario);
    Boolean existsByUsuario(String username);
    Boolean existsByDni(String dni);

    @Query("Select u from  Usuario u where u.dni =:dni  ")
    Optional<Usuario> findUsuarioByDni(@Param("dni") String dni);


}