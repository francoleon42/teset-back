package com.teset.service.impl;

import com.teset.config.jwt.JwtService;
import com.teset.dto.login.*;
import com.teset.exception.LoginException;
import com.teset.exception.NotFoundException;
import com.teset.model.UserCode;
import com.teset.repository.IUserCodeRepository;
import com.teset.repository.IUsuarioRepository;
import com.teset.service.IEmailService;
import com.teset.utils.enums.EstadoUsuario;
import com.teset.utils.enums.PropositoCode;
import com.teset.utils.enums.Rol;
import com.teset.exception.RegisterException;
import com.teset.model.Usuario;
import com.teset.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IEmailService emailService;
    private final IUsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IUserCodeRepository userCodeRepository;


    @Override
    public LoginPasoUnoResponseDTO loginStepOne(LoginRequestDTO userDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(),
                userDto.getPassword()));

        Usuario user = userRepository
                .findByUsuario(userDto.getUsername())
                .orElseThrow(() -> new NotFoundException("No se encontro el usuario con username: " + userDto.getUsername()));

        if (user.getEstadoUsuario() == EstadoUsuario.INHABILITADO) {
            throw new LoginException("El usuario esta inhabilitado");
        }
        generarCodigo(user,PropositoCode.LOGIN);

        return LoginPasoUnoResponseDTO.builder().username(userDto.getUsername()).build();
    }
    private void generarCodigo(Usuario user,PropositoCode proposito){
        Random random = new Random();
        Integer codigo = 10000 + random.nextInt(90000);

        UserCode userCode = getUserCodeByProposito(user.getUsername(), proposito);
        if(userCode == null ){
            userCode = UserCode
                    .builder()
                    .codigo(codigo)
                    .propositoCode(proposito)
                    .creacion(LocalDateTime.now())
                    .usuario(user)
                    .build();
        }

        userCode.setCodigo(codigo);
        userCodeRepository.save(userCode);

        enviarCodigoByCorreo(user.getUsuario(),proposito,codigo);
    }

    private void enviarCodigoByCorreo(String destino ,PropositoCode proposito,Integer codigo){
        String asunto = "";
        String texto="";
        if(proposito == PropositoCode.LOGIN){
            asunto = "VERIFICACION DE INICIO SESION";
            texto = "Codigo de verificacion de logueo: " + codigo;
        }
        emailService.enviarCorreo(destino, asunto, "Codigo de verificacion de logueo: " + codigo);
    }


    private UserCode getUserCodeByProposito(String username, PropositoCode proposito) {
        return userCodeRepository.findByPropositoAndUsername(username, proposito).orElse(null);
    }

    @Override
    public LoginResponseDTO loginStepTwo(CodigoVerificationRequestDTO requestDto) {
        Usuario user = userRepository
                .findByUsuario(requestDto.getUsername())
                .orElseThrow(() -> new NotFoundException("No se encontró el usuario con username: " + requestDto.getUsername()));
        UserCode userCode = getUserCodeByProposito(user.getUsername(), PropositoCode.LOGIN);
        if (userCode.getCodigo() == null || !userCode.getCodigo().equals(requestDto.getCodigo())
            || Duration.between(userCode.getCreacion(), LocalDateTime.now()).toMinutes() > 5) {
            throw new LoginException("El código de verificación es incorrecto o ha expirado");
        }

        // Limpiar el código para evitar reutilización
        userCode.setCodigo(null);
        userCodeRepository.save(userCode);

        // Generar el token
        String token = jwtService.getToken(user);

        return LoginResponseDTO
                .builder()
                .username(user.getUsuario())
                .token(token)
                .role(user.getRol())
                .build();
    }

    @Override
    public void updateStepOne(Integer id) {
        Usuario user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el usuario con id: " + id));
        generarCodigo(user,PropositoCode.REST_PASSWORD);
    }


    @Override
    public void updateStepTwo(Integer id, UpdatePasswordRequestDTO userToUpdateDto) {

        Usuario user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el usuario con id: " + id));

        UserCode userCode = getUserCodeByProposito(user.getUsername(), PropositoCode.REST_PASSWORD);
        if (userCode.getCodigo() == null || !userCode.getCodigo().equals(userToUpdateDto.getCodigo())
                || Duration.between(userCode.getCreacion(), LocalDateTime.now()).toMinutes() > 2) {
            throw new LoginException("El código de verificación es incorrecto o ha expirado");
        }

        // Limpiar el código para evitar reutilización
        userCode.setCodigo(null);
        userCodeRepository.save(userCode);

        //Actualizor el password de usuario
        Optional.ofNullable(passwordEncoder.encode(userToUpdateDto.getNewPassword())).ifPresent(user::setContrasena);
        userRepository.save(user);
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
    public void habilitar(Integer id) {
        Usuario user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el usuario con id: " + id));
        if (user.getEstadoUsuario() == EstadoUsuario.INHABILITADO) {
            user.setEstadoUsuario(EstadoUsuario.HABILITADO);
            userRepository.save(user);
        }
    }

    @Override
    public void inhabilitar(Integer id) {
        Usuario user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el usuario con id: " + id));
        if (user.getEstadoUsuario() == EstadoUsuario.HABILITADO) {
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
