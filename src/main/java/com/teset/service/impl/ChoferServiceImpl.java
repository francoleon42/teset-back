package com.teset.service.impl;

import com.teset.dto.chofer.ChoferRegistroDTO;
import com.teset.dto.chofer.ChoferResponseDTO;
import com.teset.exception.BadRequestException;
import com.teset.exception.NotFoundException;
import com.teset.model.Chofer;
import com.teset.model.Usuario;
import com.teset.repository.IChoferRepository;
import com.teset.service.IChoferService;
import com.teset.utils.enums.EstadoUsuario;
import com.teset.utils.enums.Rol;
import com.teset.utils.mappers.ChoferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoferServiceImpl implements IChoferService {

    private final IChoferRepository choferRepository;
    private final ChoferMapper choferMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void registro(ChoferRegistroDTO choferRegistroDTO) {
        // Validar el DTO
        validarChoferRegistroDTO(choferRegistroDTO);
        Usuario usuario = Usuario.builder()
                .usuario(choferRegistroDTO.getUsername())
                .contrasena(passwordEncoder.encode(choferRegistroDTO.getPassword()))
                .rol(Rol.CHOFER)
                .estadoUsuario(EstadoUsuario.INHABILITADO)
                .build();

        Chofer chofer = Chofer.builder()
                .usuario(usuario)
                .nombre(choferRegistroDTO.getNombre())
                .build();

        choferRepository.save(chofer);
    }




    @Override
    public void inhabilitarUsuarioChofer(Integer idUsuario) {
        Chofer chofer = choferRepository.findByUsuario_Id(idUsuario)
                .orElseThrow(() -> new NotFoundException("No se encontró el usuario del id " + idUsuario));

        choferRepository.save(chofer);

    }



    @Override
    public List<ChoferResponseDTO> obtenerAll() {
        return choferMapper.obtenerListaChoferDTO(choferRepository.findAll());
    }



    private void validarChoferRegistroDTO(ChoferRegistroDTO choferRegistroDTO) {
        if (choferRegistroDTO.getUsername() == null || choferRegistroDTO.getUsername().trim().isEmpty()) {
            throw new BadRequestException("El nombre de usuario no puede estar vacío.");
        }
        if (!choferRegistroDTO.getUsername().matches("^[a-zA-Z0-9]*$")) {
            throw new BadRequestException("El nombre de usuario no debe contener caracteres especiales.");
        }
        if (choferRegistroDTO.getPassword() == null || choferRegistroDTO.getPassword().trim().isEmpty()) {
            throw new BadRequestException("La contraseña no puede estar vacía.");
        }
        if (choferRegistroDTO.getNombre() == null || choferRegistroDTO.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre del chofer no puede estar vacío.");
        }
    }
}
