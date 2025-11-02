package com.cibertec.Proyecto.Clinica.Authentication.application.services;

import com.cibertec.Proyecto.Clinica.Authentication.application.ports.out.UserDetailsData;
import com.cibertec.Proyecto.Clinica.Authentication.domain.exception.CredencialesInvalidasException;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.SeguridadModel;
import com.cibertec.Proyecto.Clinica.Authentication.application.ports.in.TokenService;
import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.config.CustomUserDetails;
import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.config.UserDetailsServiceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefrescarTokenUseCase {

    private final TokenService tokenService;
    private final UserDetailsServiceAdapter userDetailsService;

    public SeguridadModel ejecutar(String refreshToken) {
        if (!tokenService.esTokenValido(refreshToken)) {
            log.warn("Intento de uso de refresh token inválido: {}", refreshToken);
            throw new CredencialesInvalidasException("Refresh token inválido o expirado");
        }
        String username = tokenService.extraerUsuario(refreshToken);
        return generarTokensDesdeUsername(username);
    }

    private SeguridadModel generarTokensDesdeUsername(String username) {
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        UserDetailsData usuario = userDetails.getUsuario();

        String accessToken = tokenService.generarTokenAcceso(usuario.getUsername(), usuario.getRoles());
        String nuevoRefreshToken = tokenService.generarTokenRefresco(usuario.getUsername(), usuario.getRoles());

        return SeguridadModel.builder()
                .token(accessToken)
                .refresh(nuevoRefreshToken)
                .build();
    }
}
