package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.in.rest.controller;

import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.in.rest.dto.*;
import com.cibertec.Proyecto.Clinica.Authentication.application.services.ObtenerUsuariosUseCase;
import com.cibertec.Proyecto.Clinica.Authentication.application.services.RegistrarUsuarioUseCase;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.RolModel;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.SeguridadModel;
import com.cibertec.Proyecto.Clinica.Authentication.application.ports.in.SeguridadService;
import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.config.JwtProperties;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.UsuarioModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/api/auth")
public class AuthController {
    private final ObtenerUsuariosUseCase obtenerUsuariosUseCase;
    private final JwtProperties jwtProperties;
    private final SeguridadService seguridadService;
    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        SeguridadModel seguridad = seguridadService.autenticacion(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(
                buildLoginResponse(
                        seguridad.getToken(),
                        seguridad.getRefresh(),
                        seguridad.getUsername(),
                        seguridad.getNombre()
                )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(@Valid @RequestBody RefreshTokenRequestDto request) {
        SeguridadModel seguridad = seguridadService.refrescar(request.getRefreshToken());
        return ResponseEntity.ok(
                buildLoginResponse(
                        seguridad.getToken(),
                        seguridad.getRefresh(),
                        seguridad.getUsername(),
                        seguridad.getNombre()
                )
        );
    }
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody RegistroRequestDto request
    ) {
        UsuarioModel usuario = UsuarioModel.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .nombre(request.nombre())
                .apellido(request.apellido())
                .roles(Set.of(
                        RolModel.builder().nombre("ROLE_USER").build()
                ))
                .build();

        registrarUsuarioUseCase.ejecutar(usuario);

        return ResponseEntity.status(201).build();
    }

    private LoginResponseDto buildLoginResponse(String accessToken, String refreshToken, String username, String nombre) {
        long accessTtlSeconds = Duration.ofMillis(jwtProperties.getAccessTokenExpiration()).toSeconds();
        return new LoginResponseDto(
                accessToken,
                refreshToken,
                accessTtlSeconds,
                username,
                nombre
        );
    }
    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDto>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<UsuarioModel> usuarios =
                obtenerUsuariosUseCase.ejecutar(PageRequest.of(page, size));

        Page<UsuarioResponseDto> response = usuarios.map(u ->
                new UsuarioResponseDto(
                        u.getId(),
                        u.getUsername(),
                        u.getNombre(),
                        u.getApellido()
                )
        );

        return ResponseEntity.ok(response);
    }

}

