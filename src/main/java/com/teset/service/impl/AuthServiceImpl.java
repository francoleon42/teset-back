package com.teset.service.impl;

import com.teset.config.jwt.JwtService;
import com.teset.dto.login.*;
import com.teset.exception.LoginException;
import com.teset.exception.NotFoundException;
import com.teset.repository.IUsuarioRepository;
import com.teset.utils.enums.EstadoUsuario;
import com.teset.utils.enums.Rol;
import com.teset.exception.RegisterException;
import com.teset.model.Usuario;
import com.teset.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public LoginResponseDTO login(LoginRequestDTO userDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(),
                userDto.getPassword()));

        Usuario user = userRepository
                .findByUsuario(userDto.getUsername())
                .orElseThrow(() -> new NotFoundException("No se encontro el usuario con username: " + userDto.getUsername()));

        if(user.getEstadoUsuario() == EstadoUsuario.INHABILITADO){
           throw new LoginException("El usuario esta inhabilitado");
        }
        String token = jwtService.getToken(user);

        return LoginResponseDTO
                .builder()
                .username(userDto.getUsername())
                .token(token)
                .role(user.getRol())
                .build();
    }





    @Override
    public LoginResponseDTO register(RegisterRequestDTO userToRegisterDto) {
        if (userRepository.existsByUsuario(userToRegisterDto.getUsername()))
            throw new RegisterException("El usuario ya existe en la base de datos.");

        Usuario user = Usuario
                .builder()
                .usuario(userToRegisterDto.getUsername())
                .contrasena(passwordEncoder.encode(userToRegisterDto.getPassword()))
                .rol(Rol.getRol(userToRegisterDto.getRole()))
                .estadoUsuario(EstadoUsuario.HABILITADO)
                .build();

        userRepository.save(user);

        return LoginResponseDTO
                .builder()
                .username(user.getUsuario())
                .token(jwtService.getToken(user))
                .role(user.getRol())
                .build();
    }

    @Override
    public void logout(String token) {
        String jwt = token.substring(7);
        jwtService.addToBlacklist(jwt);
    }

    @Override
    public void update(Integer id, UpdateRequestDTO userToUpdateDto) {
        Usuario user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el usuario con id: " + id));

        Optional.ofNullable(userToUpdateDto.getUsername()).ifPresent(user::setUsuario);
        Optional.ofNullable(passwordEncoder.encode(userToUpdateDto.getPassword())).ifPresent(user::setContrasena);

        userRepository.save(user);
    }

    @Override
    public void habilitar(Integer id) {
        Usuario user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el usuario con id: " + id));
        if(user.getEstadoUsuario() == EstadoUsuario.INHABILITADO) {
            user.setEstadoUsuario(EstadoUsuario.HABILITADO);
            userRepository.save(user);
        }
    }

    @Override
    public void inhabilitar(Integer id) {
        Usuario user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el usuario con id: " + id));
        if(user.getEstadoUsuario() == EstadoUsuario.HABILITADO) {
            user.setEstadoUsuario(EstadoUsuario.INHABILITADO);
            userRepository.save(user);
        }
    }

//    @Override
//    public void remove(Integer id) {
//        Usuario user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el usuario con id: " + id));
//        userRepository.delete(user);
//    }


    @Override
    public List<GetUserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(this::convertToGetUserDTO)
                .toList();
    }

    private GetUserDTO convertToGetUserDTO(Usuario user) {
        return GetUserDTO.builder()
                .id(user.getId())
                .username(user.getUsuario())
                .role(user.getRol())
                .estado(user.getEstadoUsuario().toString())
                .build();
    }
}
