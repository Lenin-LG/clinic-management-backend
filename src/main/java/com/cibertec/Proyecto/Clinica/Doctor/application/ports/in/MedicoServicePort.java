package com.cibertec.Proyecto.Clinica.Doctor.application.ports.in;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MedicoServicePort {
    List<Medico> listar();
    Medico obtenerPorId(Integer id);
    Medico guardar(Medico medico);
    Medico actualizar(Medico medico);
    void eliminar(Integer id);
    Page<Medico> listarPaginado(int page, int size);
    List<Medico> obtenerDisponiblesEnFecha(LocalDate fecha);
    Optional<Medico> buscarPorNombresYApellidos(String nombres, String apellidos);
    List<Medico> obtenerDisponiblesPorEspecialidadYHora(
            String especialidad,
            LocalDate fecha,
            LocalTime horaDesde
    );

}
