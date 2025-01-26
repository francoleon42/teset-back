package com.teset.config;


import com.teset.model.*;
import com.teset.repository.*;
import com.teset.utils.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Profile("!prod")
@Component
@RequiredArgsConstructor
public class Bootstrap implements ApplicationRunner {
    private final IUsuarioRepository userRepository;
    private final INovedadRepository novedadRepository;
    private final IContactoRepository contactoRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Crear usuarios con builder
        Usuario root = Usuario.builder()
                .usuario("francoleon2001@gmail.com")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.CLIENTE)
                .alta(LocalDate.now())
                .dni("2400042")
                .codDispositivo("codigoBoostrap")
                .build();

        userRepository.saveAll(List.of(root));


        // Crear novedades
        Novedad novedad1 = Novedad.builder()
                .titulo("Promoción de verano")
                .linkImagen("https://example.com/images/verano.jpg")
                .linkComercio("https://example.com/comercio/verano")
                .build();

        Novedad novedad2 = Novedad.builder()
                .titulo("Nueva colección")
                .linkImagen("https://example.com/images/coleccion.jpg")
                .linkComercio("https://example.com/comercio/coleccion")
                .build();

        novedadRepository.saveAll(List.of(novedad1, novedad2));

        // Crear contactos
        Contacto contacto1 = Contacto.builder()
                .titulo("Soporte técnico")
                .subTitulo("Contacta con nuestro equipo")
                .tipo(TipoContacto.TELEFONO)
                .logoLink("ww.linkLogoEjemplo.com")
                .build();

        Contacto contacto2 = Contacto.builder()
                .titulo("Síguenos en Instagram")
                .subTitulo("Descubre más contenido")
                .tipo(TipoContacto.REDES_SOCIALES)
                .logoLink("https://instagram.com/example")
                .build();

        Contacto contacto3 = Contacto.builder()
                .titulo("Atención por WhatsApp")
                .subTitulo("Resolvemos tus dudas")
                .tipo(TipoContacto.WHATSAPP)
                .logoLink("https://wa.me/123456789")
                .build();

        contactoRepository.saveAll(List.of(contacto1, contacto2, contacto3));
    }

}
