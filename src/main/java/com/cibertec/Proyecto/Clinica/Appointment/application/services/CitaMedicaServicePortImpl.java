package com.cibertec.Proyecto.Clinica.Appointment.application.services;

import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.in.rest.dto.CitaMedicaDTO;
import com.cibertec.Proyecto.Clinica.Appointment.domain.Model.CitaMedica;
import com.cibertec.Proyecto.Clinica.Appointment.application.ports.out.CitaMedicaPersistencePort;
import com.cibertec.Proyecto.Clinica.Appointment.application.ports.in.CitaMedicaServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitaMedicaServicePortImpl implements CitaMedicaServicePort {

    private final CitaMedicaPersistencePort citaMedicaPersistencePort;

    @Override
    public CitaMedica registrarCita(CitaMedica citaMedica) {
        citaMedica.setFechaCreacion(LocalDateTime.now());
        citaMedica.setUsuarioCreacion("admin");
        return citaMedicaPersistencePort.save(citaMedica);
    }

    @Override
    public Optional<CitaMedica> obtenerCitaPorId(Integer id) {
        return citaMedicaPersistencePort.findById(id);
    }

    /* @Override
    public List<CitaMedica> listarCitas() {
        return citaMedicaRepository.findAll();
    }*/

    @Override
    public void eliminarCita(Integer id) {
        CitaMedica citaMedica= citaMedicaPersistencePort.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        citaMedicaPersistencePort.deleteById(id);
    }

    @Override
    public CitaMedica actualizarCita(CitaMedica citaMedica) {
        citaMedica.setFechaActualizacion(LocalDateTime.now());
        citaMedica.setUsuarioActualizacion("admin_update");
        return citaMedicaPersistencePort.update(citaMedica);
    }

    @Override
    public Page<CitaMedicaDTO> listarCitasPaginadas(Pageable pageable) {
        return citaMedicaPersistencePort.findAllPaginado(pageable);
    }
    @Override
    public List<CitaMedicaDTO> listarCitasPorMedicoYFecha(Integer medicoId, LocalDate fecha) {
        return citaMedicaPersistencePort.findByMedicoAndFecha(medicoId, fecha);
    }
}
