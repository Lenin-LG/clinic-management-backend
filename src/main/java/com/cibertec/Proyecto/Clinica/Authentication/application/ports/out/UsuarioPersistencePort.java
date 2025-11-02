package com.cibertec.Proyecto.Clinica.Authentication.application.ports.out;

import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.UsuarioModel;

import java.util.Optional;

/**
 * User Aggregate Repository Agreement
 * Responsibilities:
 * - Provide user access based on domain query criteria.
 * - Manage auxiliary aggregate data (e.g., token cache if applicable to the domain).
 * This agreement should NOT expose infrastructure details.
 */
public interface UsuarioPersistencePort {
    Optional<UsuarioModel> usuarioPorUserName(String username);

    void guardarToken(String token);

    String obtenerTokenCache(String username);
}
