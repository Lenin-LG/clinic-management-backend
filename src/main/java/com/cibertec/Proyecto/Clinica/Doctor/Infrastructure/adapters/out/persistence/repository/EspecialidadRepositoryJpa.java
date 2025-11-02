package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.repository;

import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.EspecialidadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EspecialidadRepositoryJpa extends JpaRepository<EspecialidadEntity,Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE EspecialidadEntity e " +
            "SET e.nombre = :nombre, e.descripcion = :descripcion " +
            "WHERE e.id = :id")
    int actualizarEspecialidad(Integer id, String nombre, String descripcion);
    @Query("""
        SELECT e
        FROM EspecialidadEntity e
        ORDER BY e.nombre ASC
    """)
    Page<EspecialidadEntity> findAllPaginado(Pageable pageable);
}
