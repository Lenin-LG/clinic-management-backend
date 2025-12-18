package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.in.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistroRequestDto(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String email,
        @NotBlank String nombre,
        @NotBlank String apellido
) {}