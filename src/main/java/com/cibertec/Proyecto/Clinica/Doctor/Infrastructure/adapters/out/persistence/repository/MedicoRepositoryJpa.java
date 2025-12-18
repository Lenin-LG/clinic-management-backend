package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.repository;
import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.MedicoEntity;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepositoryJpa extends JpaRepository<MedicoEntity, Integer> {

    @Query("SELECT m FROM MedicoEntity m JOIN FETCH m.especialidad e")
    Page<MedicoEntity> findAllMedicosConEspecialidad(Pageable pageable);

    @Query("""
    SELECT m
    FROM MedicoEntity m
    JOIN FETCH m.especialidad e
    WHERE m.id NOT IN (
        SELECT c.medico.id
        FROM CitaMedicaEntity c
        WHERE c.fecha = :fecha
    )
    ORDER BY m.apellidos ASC
""")
    List<MedicoEntity> findMedicosDisponiblesEnFecha(LocalDate fecha);
    @Query("""
    SELECT m FROM MedicoEntity m
    WHERE LOWER(m.nombres) = LOWER(:nombres)
      AND LOWER(m.apellidos) = LOWER(:apellidos)
""")
    Optional<MedicoEntity> buscarPorNombresYApellidos(
            @Param("nombres") String nombres,
            @Param("apellidos") String apellidos
    );
    @Query("""
SELECT m
FROM MedicoEntity m
JOIN m.especialidad e
WHERE LOWER(e.nombre) = LOWER(:especialidad)
AND m.id NOT IN (
    SELECT c.medico.id
    FROM CitaMedicaEntity c
    WHERE c.fecha = :fecha
      AND c.hora >= :horaDesde
)
ORDER BY m.apellidos ASC
""")
    List<MedicoEntity> findDisponiblesPorEspecialidadYHora(
            @Param("especialidad") String especialidad,
            @Param("fecha") LocalDate fecha,
            @Param("horaDesde") LocalTime horaDesde
    );


}
