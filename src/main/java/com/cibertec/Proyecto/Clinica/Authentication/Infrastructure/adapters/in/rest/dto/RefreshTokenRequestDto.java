package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO de solicitud para refrescar el token.
 */
@Data
public class RefreshTokenRequestDto {
    /** Refresh Token vigente */
    @NotBlank(message = "El refresh token es obligatorio")
    private String refreshToken;
}
