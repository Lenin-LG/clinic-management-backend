package com.cibertec.Proyecto.Clinica.Appointment.application.ports.out;

import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.in.rest.dto.CitaMedicaDTO;
import com.cibertec.Proyecto.Clinica.Appointment.domain.Model.CitaMedica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CitaMedicaPersistencePort {
    CitaMedica save(CitaMedica citaMedica);
    Optional<CitaMedica> findById(Integer id);
    List<CitaMedica> findAll();
    void deleteById(Integer id);
    @Transactional
    CitaMedica update(CitaMedica citaMedica);
    Page<CitaMedicaDTO> findAllPaginado(Pageable pageable);
    List<CitaMedicaDTO> findByMedicoAndFecha(Integer medicoId, LocalDate fecha);
}
