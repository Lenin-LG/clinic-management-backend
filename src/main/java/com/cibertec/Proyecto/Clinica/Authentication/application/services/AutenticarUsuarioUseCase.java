package com.cibertec.Proyecto.Clinica.Authentication.application.services;

import com.cibertec.Proyecto.Clinica.Authentication.application.ports.out.UserDetailsData;
import com.cibertec.Proyecto.Clinica.Authentication.domain.exception.CredencialesInvalidasException;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.SeguridadModel;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.UsuarioModel;
import com.cibertec.Proyecto.Clinica.Authentication.application.ports.in.TokenService;
import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.config.CustomUserDetails;
import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.config.UserDetailsServiceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutenticarUsuarioUseCase {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserDetailsServiceAdapter userDetailsService;

    public SeguridadModel ejecutar(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception ex) {
            log.warn("Autenticación fallida para el usuario '{}'", username, ex);
            throw new CredencialesInvalidasException("Credenciales inválidas");
        }

        return generarTokensDesdeUsername(username);
    }

    private SeguridadModel generarTokensDesdeUsername(String username) {
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        UserDetailsData usuario = userDetails.getUsuario();

        String accessToken = tokenService.generarTokenAcceso(usuario.getUsername(), usuario.getRoles());
        String refreshToken = tokenService.generarTokenRefresco(usuario.getUsername(), usuario.getRoles());

        return SeguridadModel.builder()
                .token(accessToken)
                .refresh(refreshToken)
                .username(usuario.getUsername())
                .nombre(usuario.NombreCompleto())
                .build();
    }
}
