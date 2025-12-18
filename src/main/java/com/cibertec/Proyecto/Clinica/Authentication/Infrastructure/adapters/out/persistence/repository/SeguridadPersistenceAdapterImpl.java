package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.out.persistence.repository;


import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.out.persistence.entity.RolEntity;
import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.out.persistence.entity.UsuarioEntity;
import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.out.persistence.entity.UsuarioRolEntity;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.RolModel;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.UsuarioModel;
import com.cibertec.Proyecto.Clinica.Authentication.application.ports.out.UsuarioPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SeguridadPersistenceAdapterImpl implements UsuarioPersistencePort {

    private final UsuarioRepositoryJpa usuarioRepositoryJpa;
    private final RolRepositoryJpa rolRepositoryJpa;
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
    public void guardarUsuario(UsuarioModel usuario) {

        UsuarioEntity entity = new UsuarioEntity();
        entity.setUsername(usuario.getUsername());
        entity.setEmail(usuario.getEmail());
        entity.setPasswordHash(usuario.getPassword());
        entity.setNombre(usuario.getNombre());
        entity.setApellido(usuario.getApellido());
        entity.setActivo(true);

        // Rol por defecto (ej: ROLE_USER)
        RolEntity rol = rolRepositoryJpa
                .findByNombreAndActivoTrue("DOCTOR")
                .orElseThrow(() ->
                        new IllegalStateException("El rol DOCTOR no existe")
                );

        UsuarioRolEntity ur = new UsuarioRolEntity();
        ur.setUsuario(entity);
        ur.setRol(rol);
        ur.setActivo(true);

        entity.setRoles(Set.of(ur));

        usuarioRepositoryJpa.save(entity);
    }

    @Override
    public void guardarToken(String token) {
        log.info(token);
    }

    @Override
    public Page<UsuarioModel> obtenerUsuarios(Pageable pageable) {
        return usuarioRepositoryJpa.findAll(pageable)
                .map(u -> UsuarioModel.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .nombre(u.getNombre())
                        .apellido(u.getApellido())
                        .build()
                );
    }


    @Override
    public String obtenerTokenCache(String username) {
        return "";
    }
}
