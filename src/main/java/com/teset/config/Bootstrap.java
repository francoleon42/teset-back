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

     //Perfil para registrarse con francoleon2001@gmail.com usando el dni de daniel al registrarse 14430558
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Usuario root = Usuario.builder()
//
//                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
//                .rol(Rol.CLIENTE)
//                .alta(LocalDate.now())
//                .codDispositivo("codigoBoostrap")
//                .usuario("costantinifranco2001@gmail.com")
//                .dni("14430557")
//
//                .build();
//
//        userRepository.saveAll(List.of(root));

    // Perfil para usar para crear un usuario con mi correo usando el dni de daniel

        Usuario root = Usuario.builder()
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.CLIENTE)
                .alta(LocalDate.now())
                .codDispositivo("codigoBoostrap")
                .dni("14430558")
                .usuario("francoleon2001@gmail.com")
                .build();

        userRepository.saveAll(List.of(root));


        // Crear novedades
        Novedad novedad1 = Novedad.builder()
                .titulo("SoloDeportes")
                .linkImagen("https://media2.solodeportes.com.ar/media/slider/slide/18291311_CAI_Training_1920x540_copia.webp")
                .linkComercio("https://www.solodeportes.com.ar")
                .mostrarEnInicio(true)
                .build();

        Novedad novedad2 = Novedad.builder()
                .titulo("SoloDeportes")
                .linkImagen("https://media2.solodeportes.com.ar/media/slider/slide/08410911_Solo_Deportes_Desktop_BTS_copia.webp")
                .linkComercio("https://www.solodeportes.com.ar")
                .mostrarEnInicio(true)
                .build();

        Novedad novedad3 = Novedad.builder()
                .titulo("SoloDeportes")
                .linkImagen("https://media2.solodeportes.com.ar/media/slider/slide/09042610_desktop.webp")
                .linkComercio("https://www.solodeportes.com.ar")
                .mostrarEnInicio(true)
                .build();

        Novedad novedad4 = Novedad.builder()
                .titulo("SoloDeportes")
                .linkImagen("https://media2.solodeportes.com.ar/media/slider/slide/18311211_DESKTOP.webp")
                .linkComercio("https://www.solodeportes.com.ar")
                .mostrarEnInicio(true)
                .build();

        Novedad novedad5 = Novedad.builder()
                .titulo("SoloDeportes")
                .linkImagen("https://media2.solodeportes.com.ar/media/slider/slide/12361208_SD_Banner_desktop.webp")
                .linkComercio("https://www.solodeportes.com.ar")
                .mostrarEnInicio(false)
                .build();


        novedadRepository.saveAll(List.of(novedad1, novedad2, novedad3, novedad4, novedad5));

        // Crear contactos
        Contacto contacto1 = Contacto.builder()
                .titulo("Facebook")
                .subTitulo("facebook.com/teset")
                .logoLink("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/2021_Facebook_icon.svg/2048px-2021_Facebook_icon.svg.png")
                .tipo(TipoContacto.REDES_SOCIALES)
                .link("")
                .build();

        Contacto contacto2 = Contacto.builder()
                .titulo("Instagram")
                .subTitulo("@teset")
                .logoLink("https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Instagram_logo_2016.svg/1200px-Instagram_logo_2016.svg.png")
                .tipo(TipoContacto.REDES_SOCIALES)
                .link("")
                .build();

        Contacto contacto3 = Contacto.builder()
                .titulo("11-3060-4587")
                .subTitulo("Prestamos")
                .logoLink("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/WhatsApp.svg/767px-WhatsApp.svg.png")
                .tipo(TipoContacto.WHATSAPP)
                .link("")
                .build();

        Contacto contacto4 = Contacto.builder()
                .titulo("11-3060-4287")
                .subTitulo("Deudas y Pagos")
                .logoLink("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/WhatsApp.svg/767px-WhatsApp.svg.png")
                .tipo(TipoContacto.WHATSAPP)
                .link("")
                .build();

        Contacto contacto5 = Contacto.builder()
                .titulo("15-3000-4000")
                .subTitulo("Nuevos prestamos")
                .logoLink("https://cdn.pixabay.com/photo/2017/01/10/03/54/icon-1968244_1280.png")
                .tipo(TipoContacto.TELEFONO)
                .link("")
                .build();

        Contacto contacto6 = Contacto.builder()
                .titulo("15-3000-4000")
                .subTitulo("Nuevos prestamos")
                .logoLink("https://cdn.pixabay.com/photo/2017/01/10/03/54/icon-1968244_1280.png")
                .tipo(TipoContacto.TELEFONO)
                .link("")
                .build();

        contactoRepository.saveAll(List.of(contacto1, contacto2, contacto3, contacto4, contacto5, contacto6));
    }

}
