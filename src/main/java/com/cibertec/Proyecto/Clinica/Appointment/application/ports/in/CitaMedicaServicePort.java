package com.cibertec.Proyecto.Clinica.Appointment.application.ports.in;

import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.in.rest.dto.CitaMedicaDTO;
import com.cibertec.Proyecto.Clinica.Appointment.domain.Model.CitaMedica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CitaMedicaServicePort {
    //List<CitaMedica> listarCitas();
    Optional<CitaMedica> obtenerCitaPorId(Integer id);
    CitaMedica registrarCita(CitaMedica citaMedica);
    CitaMedica actualizarCita(CitaMedica citaMedica);
    void eliminarCita(Integer id);
    Page<CitaMedicaDTO> listarCitasPaginadas(Pageable pageable);
}
