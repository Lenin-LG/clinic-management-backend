package com.cibertec.Proyecto.Clinica.Authentication.application.services;

import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.config.CustomUserDetails;
import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.config.UserDetailsServiceAdapter;
import com.cibertec.Proyecto.Clinica.Authentication.application.ports.in.TokenService;
import com.cibertec.Proyecto.Clinica.Authentication.application.ports.out.UserDetailsData;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.SeguridadModel;
import com.cibertec.Proyecto.Clinica.Authentication.domain.exception.CredencialesInvalidasException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefrescarTokenUseCaseTest {
    @Mock
    private TokenService tokenService;

    @Mock
    private UserDetailsServiceAdapter userDetailsService;

    @InjectMocks
    private RefrescarTokenUseCase refrescarTokenUseCase;

    @Test
    void generateTokensTest() {
        // --- Given ---
        String refreshToken = "valid-refresh-token";
        String username = "usuario@example.com";

        UserDetailsData userData = new UserDetailsData() {

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String NombreCompleto() {
                return "";
            }

            @Override
            public String getPassword() {
                return "pass";
            }

            @Override
            public boolean isActive() {
                return true;
            }

            @Override
            public Collection<String> getRoles() {
                return List.of("ROLE_USER");
            }
        };

        CustomUserDetails userDetails = new CustomUserDetails(userData);

        when(tokenService.esTokenValido(refreshToken)).thenReturn(true);
        when(tokenService.extraerUsuario(refreshToken)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(tokenService.generarTokenAcceso(any(), any())).thenReturn("access-token");
        when(tokenService.generarTokenRefresco(any(), any())).thenReturn("refresh-token");

        // --- When ---
        SeguridadModel result = refrescarTokenUseCase.ejecutar(refreshToken);

        // --- Then ---
        assertNotNull(result);
        assertEquals("access-token", result.getToken());
        assertEquals("refresh-token", result.getRefresh());

        verify(tokenService).esTokenValido(refreshToken);
        verify(tokenService).extraerUsuario(refreshToken);
        verify(userDetailsService).loadUserByUsername(username);
    }

    @Test
    void tokensExceptionTest() {
        // --- Given ---
        String invalidToken = "invalid";
        when(tokenService.esTokenValido(invalidToken)).thenReturn(false);

        // --- When & Then ---
        assertThrows(CredencialesInvalidasException.class,
                () -> refrescarTokenUseCase.ejecutar(invalidToken));

        verify(tokenService, never()).extraerUsuario(any());
        verify(userDetailsService, never()).loadUserByUsername(any());
    }
}