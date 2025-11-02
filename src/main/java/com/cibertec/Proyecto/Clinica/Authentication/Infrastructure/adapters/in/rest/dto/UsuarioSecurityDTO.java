package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.in.rest.dto;
import java.util.Set;

public record UsuarioSecurityDTO(
        String username,
        String password,
        boolean activo,
        Set<String> roles
) {}