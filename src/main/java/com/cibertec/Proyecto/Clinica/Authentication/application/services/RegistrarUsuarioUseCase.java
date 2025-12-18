package com.cibertec.Proyecto.Clinica.Authentication.application.services;

import com.cibertec.Proyecto.Clinica.Authentication.application.ports.out.UsuarioPersistencePort;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.UsuarioModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrarUsuarioUseCase {

    private final UsuarioPersistencePort usuarioPersistencePort;
    private final PasswordEncoder passwordEncoder;

    public void ejecutar(UsuarioModel usuario) {

        // 1. Validar si ya existe
        usuarioPersistencePort.usuarioPorUserName(usuario.getUsername())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("El usuario ya existe");
                });

        // 2. Encriptar password
        UsuarioModel usuarioSeguro = UsuarioModel.builder()
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .password(passwordEncoder.encode(usuario.getPassword()))
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .activo(true)
                .roles(usuario.getRoles())
                .build();

        // 3. Guardar
        usuarioPersistencePort.guardarUsuario(usuarioSeguro);
    }
}
