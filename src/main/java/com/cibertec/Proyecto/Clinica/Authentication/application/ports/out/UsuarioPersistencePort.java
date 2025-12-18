package com.cibertec.Proyecto.Clinica.Authentication.application.ports.out;

import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    void guardarUsuario(UsuarioModel usuario);
    void guardarToken(String token);
    Page<UsuarioModel> obtenerUsuarios(Pageable pageable);
    String obtenerTokenCache(String username);
}
