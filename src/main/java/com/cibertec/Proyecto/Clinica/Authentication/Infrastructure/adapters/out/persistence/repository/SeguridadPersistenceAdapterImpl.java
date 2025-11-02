package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.out.persistence.repository;


import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.RolModel;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.UsuarioModel;
import com.cibertec.Proyecto.Clinica.Authentication.application.ports.out.UsuarioPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SeguridadPersistenceAdapterImpl implements UsuarioPersistencePort {

    private final UsuarioRepositoryJpa usuarioRepositoryJpa;

    @Override
    public Optional<UsuarioModel> usuarioPorUserName(String username) {
        return usuarioRepositoryJpa.usuarioPorUsername(username)
                .map(u -> UsuarioModel.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .email(u.getEmail())
                        .password(u.getPasswordHash())
                        .apellido(u.getApellido())
                        .nombre(u.getNombre())
                        .activo(u.getActivo())
                        .roles(u.getRoles()
                                .stream()
                                .map(r -> RolModel.builder()
                                        .nombre(r.getRol().getNombre())
                                        .descripcion(r.getRol().getDescripcion())
                                        .build()
                                )
                                .collect(Collectors.toSet())
                        )
                        .build());
    }

    @Override
    public void guardarToken(String token) {
        log.info(token);
    }

    @Override
    public String obtenerTokenCache(String username) {
        return "";
    }
}
