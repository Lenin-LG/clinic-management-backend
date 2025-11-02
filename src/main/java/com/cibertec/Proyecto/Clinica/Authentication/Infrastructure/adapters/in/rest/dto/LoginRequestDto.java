package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO de solicitud para login.
 */
@Data
public class LoginRequestDto {
    /** Nombre de usuario o identificador de acceso */
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;
    /** Contraseña del usuario */
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
