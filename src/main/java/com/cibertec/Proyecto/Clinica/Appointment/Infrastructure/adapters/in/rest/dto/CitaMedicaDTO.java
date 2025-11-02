package com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.in.rest.dto;

import com.cibertec.Proyecto.Clinica.Appointment.domain.enums.EstadoCita;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class CitaMedicaDTO {
    private Integer id;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer idPaciente;
    private Integer idMedico;
    private String pacienteNombreCompleto;
    private String medicoNombreCompleto;
    private String especialidad;
    private String motivo;
    private EstadoCita estado;
    public CitaMedicaDTO() {
    }
    public CitaMedicaDTO(Integer id, LocalDate fecha, LocalTime hora,
                         Integer idPaciente, Integer idMedico, String pacienteNombreCompleto, String medicoNombreCompleto,
                         String especialidad, String motivo, EstadoCita estado) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.pacienteNombreCompleto = pacienteNombreCompleto;
        this.medicoNombreCompleto = medicoNombreCompleto;
        this.especialidad = especialidad;
        this.motivo = motivo;
        this.estado = estado;
    }
}
