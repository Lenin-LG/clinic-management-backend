package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.in.rest.dto;

public record UsuarioResponseDto(
        Long id,
        String username,
        String nombre,
        String apellido
) {}

