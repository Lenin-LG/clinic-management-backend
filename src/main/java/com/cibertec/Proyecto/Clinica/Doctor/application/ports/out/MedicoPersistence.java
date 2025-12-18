package com.cibertec.Proyecto.Clinica.Doctor.application.ports.out;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MedicoPersistence {
    Medico save(Medico medico);
    Optional<Medico> findById(Integer id);
    List<Medico> findAll();
    void deleteById(Integer id);
    @Transactional
    Medico update(Medico medico);
    Page<Medico> listarPaginado(Pageable pageable);
    List<Medico> findDisponiblesEnFecha(LocalDate fecha);
    Optional<Medico> findByNombresYApellidos(String nombres, String apellidos);
    List<Medico> findDisponiblesPorEspecialidadYHora(
            String especialidad,
            LocalDate fecha,
            LocalTime horaDesde
    );

}
