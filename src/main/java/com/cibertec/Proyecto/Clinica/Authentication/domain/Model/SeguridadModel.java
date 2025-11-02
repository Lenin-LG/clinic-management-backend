package com.cibertec.Proyecto.Clinica.Authentication.domain.Model;

import lombok.Builder;
import lombok.Data;

/**
 * Respuesta de seguridad que contiene los tokens emitidos.
 * - token: Access Token JWT.
 * - refresh: Refresh Token JWT.
 */
@Data
@Builder
public class SeguridadModel {
    private String token;
    private String refresh;
}
