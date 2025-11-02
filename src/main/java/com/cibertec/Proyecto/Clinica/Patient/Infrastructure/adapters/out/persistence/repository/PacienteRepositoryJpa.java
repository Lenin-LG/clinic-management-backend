package com.cibertec.Proyecto.Clinica.Patient.Infrastructure.adapters.out.persistence.repository;

import com.cibertec.Proyecto.Clinica.Patient.Infrastructure.adapters.out.persistence.entity.PacienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PacienteRepositoryJpa extends JpaRepository<PacienteEntity, Integer> {

    @Modifying
    @Query("UPDATE PacienteEntity p SET p.nombres = :nombres, p.apellidos = :apellidos, " +
            "p.fechaNacimiento = :fechaNacimiento, p.dni = :dni, p.direccion = :direccion, " +
            "p.telefono = :telefono, p.email = :email, " +
            "p.fechaActualizacion = CURRENT_DATE, p.usuarioActualizacion = :usuario " +
            "WHERE p.id = :id")
    int actualizarPaciente(
            @Param("id") Integer id,
            @Param("nombres") String nombres,
            @Param("apellidos") String apellidos,
            @Param("fechaNacimiento") LocalDate fechaNacimiento,
            @Param("dni") String dni,
            @Param("direccion") String direccion,
            @Param("telefono") String telefono,
            @Param("email") String email,
            @Param("usuario") String usuario
    );

    @Query("""
        SELECT p 
        FROM PacienteEntity p
        ORDER BY p.apellidos ASC
    """)
        Page<PacienteEntity> findAllPaginado(Pageable pageable);
}