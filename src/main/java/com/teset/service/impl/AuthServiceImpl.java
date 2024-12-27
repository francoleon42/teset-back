package com.teset.service.impl;

import com.teset.config.jwt.JwtService;
import com.teset.dto.cliente.GetClienteResponseDTO;
import com.teset.dto.auth.*;
import com.teset.exception.LoginException;
import com.teset.exception.NotFoundException;
import com.teset.model.UserCode;
import com.teset.repository.IUserCodeRepository;
import com.teset.repository.IUsuarioRepository;
import com.teset.service.IClienteService;
import com.teset.service.IEmailService;
import com.teset.utils.enums.EstadoCliente;
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
import java.time.LocalDate;
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
    private final IClienteService clienteService;

    @Override
    public LoginPasoUnoResponseDTO loginStepOne(LoginRequestDTO userDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(),
                userDto.getPassword()));

        Usuario user = userRepository
                .findByUsuario(userDto.getUsername())
                .orElseThrow(() -> new NotFoundException("No se encontro el usuario con username: " + userDto.getUsername()));


        generarCodigo(user.getUsername(), PropositoCode.LOGIN);

        return LoginPasoUnoResponseDTO.builder().username(userDto.getUsername()).build();
    }

    private void generarCodigo(String username, PropositoCode proposito) {
        Random random = new Random();
        Integer codigo = 10000 + random.nextInt(90000);

        UserCode userCode = getUserCodeByProposito(username, proposito);

        Usuario user = userRepository
                .findByUsuario(username)
                .orElseThrow(() -> new NotFoundException("No se encontro el usuario con username: " + username));

        if (userCode == null) {
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

        enviarCodigoByCorreo(user.getUsuario(), proposito, codigo);
    }

    private void enviarCodigoByCorreo(String destino, PropositoCode proposito, Integer codigo) {
        String asunto = "";
        String texto = "";
        if (proposito == PropositoCode.LOGIN) {
            asunto = "VERIFICACION DE INICIO SESION";
            texto = "Codigo de verificacion de logueo: " + codigo;
        }
        if (proposito == PropositoCode.LOGIN) {
            asunto = "VERIFICACION DE INICIO SESION";
            texto = "Codigo de verificacion de logueo: " + codigo;
        }
        if (proposito == PropositoCode.REGISTER) {
            asunto = "VERIFICACION DE REGISTRO";
            texto = "Codigo de verificacion de registro: " + codigo;
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
        generarCodigo(user.getUsername(), PropositoCode.REST_PASSWORD);
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
    public LoginResponseDTO registerStepOne(RegisterRequestDTO userToRegisterDto) {
        // verificacion de si el usuario esta registrado anteriormente por el sistema principal
        // obtncion del cliente
        GetClienteResponseDTO cliente = clienteService.getCliente(userToRegisterDto.getDni());
        if (cliente.getEstado().toString().equals(EstadoCliente.NO_DISPONIBLE.toString())) {
            throw new RegisterException("El usuario no es cliente");
        }

        if(userRepository.existsByDni(userToRegisterDto.getDni())){
                throw new RegisterException("El usuario ya esta registrado");
        }


        Usuario user = Usuario
                .builder()
                .usuario(cliente.getEmail())
                .dni(userToRegisterDto.getDni())
                .rol(Rol.CLIENTE)
                .build();

        userRepository.save(user);

       generarCodigo(cliente.getEmail(), PropositoCode.REGISTER);

        return LoginResponseDTO
                .builder()
                .username(user.getUsuario())
                .token(jwtService.getToken(user))
                .role(user.getRol())
                .build();
    }

    @Override
    public LoginResponseDTO registerStepTwo(RegisterTwoRequestDTO userRegisterTwo) {
        Usuario user = userRepository
                .findByUsuario(userRegisterTwo.getUsername())
                .orElseThrow(() -> new NotFoundException("No se encontro el usuario con username: " + userRegisterTwo.getUsername()));

        UserCode userCode = getUserCodeByProposito(user.getUsername(), PropositoCode.REGISTER);
        if (userCode.getCodigo() == null || !userCode.getCodigo().equals(userRegisterTwo.getCodigo())
                || Duration.between(userCode.getCreacion(), LocalDateTime.now()).toMinutes() > 2) {
            throw new LoginException("El código de verificación es incorrecto o ha expirado");
        }

        // Limpiar el código para evitar reutilización
        userCode.setCodigo(null);
        userCodeRepository.save(userCode);

        // agrego la contraseña
        user.setContrasena(passwordEncoder.encode(userRegisterTwo.getPassword()));
        user.setAlta(LocalDate.now());
        userRepository.save(user);
//
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
                .build();
    }
}
