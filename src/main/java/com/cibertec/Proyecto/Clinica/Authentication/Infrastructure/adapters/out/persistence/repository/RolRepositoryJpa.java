package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.out.persistence.repository;


import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.out.persistence.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepositoryJpa extends JpaRepository<RolEntity, Long> {

    Optional<RolEntity> findByNombreAndActivoTrue(String nombre);
}
