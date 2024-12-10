package com.teset.testUnitario.repository;

import com.teset.model.Usuario;
import com.teset.repository.IUsuarioRepository;
import com.teset.utils.enums.EstadoUsuario;
import com.teset.utils.enums.Rol;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Test
    @Transactional
    @Rollback
    void guardarUsuario() {
        Usuario usuario = Usuario.builder()
                .usuario("operador")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.OPERADOR)
                .estadoUsuario(EstadoUsuario.HABILITADO)
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        assertNotNull(usuarioGuardado);
        assertNotNull(usuarioGuardado.getId());
        assertEquals("operador", usuarioGuardado.getUsuario());
        assertEquals(Rol.OPERADOR, usuarioGuardado.getRol());
    }

    @Test
    void encontrarUsuarioPorNombre() {
        Usuario usuario = Usuario.builder()
                .usuario("operador")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.OPERADOR)
                .estadoUsuario(EstadoUsuario.HABILITADO)
                .build();

        usuarioRepository.save(usuario);

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByUsuario("operador");

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("operador", usuarioEncontrado.get().getUsuario());
    }

    @Test
    void existeUsuarioPorNombre() {
        Usuario usuario = Usuario.builder()
                .usuario("operador")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.OPERADOR)
                .estadoUsuario(EstadoUsuario.HABILITADO)
                .build();

        usuarioRepository.save(usuario);

        Boolean existeUsuario = usuarioRepository.existsByUsuario("operador");

        assertTrue(existeUsuario);
    }
}
