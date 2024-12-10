package com.teset.config;


import com.teset.model.*;
import com.teset.repository.*;
import com.teset.utils.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("!prod")
@Component
@RequiredArgsConstructor
public class Bootstrap implements ApplicationRunner {
    private final IUsuarioRepository userRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Crear usuarios con builder
        Usuario root = Usuario.builder()
                .usuario("francoleon2001@gmail.com")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .estadoUsuario(EstadoUsuario.HABILITADO)
                .rol(Rol.CLIENTE)
                .build();

        userRepository.saveAll(List.of(root));

    }

}
