package com.cibertec.Proyecto.Clinica.AI.Tools;

import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.in.rest.dto.CitaMedicaDTO;
import com.cibertec.Proyecto.Clinica.Appointment.application.ports.in.CitaMedicaServicePort;
import com.cibertec.Proyecto.Clinica.Appointment.domain.Model.CitaMedica;
import com.cibertec.Proyecto.Clinica.Appointment.domain.enums.EstadoCita;
import com.cibertec.Proyecto.Clinica.Doctor.application.ports.in.MedicoServicePort;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import com.cibertec.Proyecto.Clinica.Patient.application.ports.in.PacienteService;
import com.cibertec.Proyecto.Clinica.Patient.domain.Model.Paciente;
import com.cibertec.Proyecto.Clinica.WebSocket.service.NotificacionWsService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CitaTools {

    private final CitaMedicaServicePort citaService;
    private final PacienteService pacienteService;
    private final MedicoServicePort medicoService;
    private final NotificacionWsService notificacionWsService;
    /* =========================
       CREAR CITA CON DATOS HUMANOS
       ========================= */
    @Tool
    public String crearCitaPorDatosHumanos(
            String dniPaciente,
            String nombreMedicoCompleto,
            String fecha,
            String hora,
            String motivo,
            String usuarioCreacion
    ) {

        // ️ Buscar o crear paciente por DNI
        Paciente paciente = pacienteService
                .buscarPorDni(dniPaciente)
                .orElse(null);

        // Separar nombre completo del médico
        String[] partes = separarNombreCompleto(nombreMedicoCompleto);

        // Buscar médico exacto (nombres + apellidos)
        Medico medico = medicoService
                .buscarPorNombresYApellidos(partes[0], partes[1])
                .orElse(null);

        if (medico == null) {
            return "No se encontró ningún médico con el nombre " + nombreMedicoCompleto;
        }

        // Crear cita
        CitaMedica cita = new CitaMedica();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFecha(LocalDate.parse(fecha));
        cita.setHora(LocalTime.parse(hora));
        cita.setMotivo(motivo);
        cita.setEstado(EstadoCita.PENDIENTE);
        cita.setFechaCreacion(LocalDateTime.now());
        cita.setUsuarioCreacion(usuarioCreacion);

        citaService.registrarCita(cita);


        notificacionWsService.notificarCitaCreada(
                "Nueva cita creada:\n" +
                        "Paciente: " + paciente.getNombres() + "\n" +
                        "Doctor: Dr. " + medico.getNombres() + " " + medico.getApellidos() + "\n" +
                        "Fecha: " + fecha + " " + hora
        );

        return "Cita creada con el Dr. "
                + medico.getNombres() + " " + medico.getApellidos()
                + " para el "
                + fecha
                + " a las "
                + hora;
    }

    /* =========================
       CITAS DE UN MÉDICO EN FECHA
       ========================= */
    @Tool
    public String citasDelMedicoEnFecha(
            String nombreMedicoCompleto,
            String fecha
    ) {

        // Separar nombre completo
        String[] partes = separarNombreCompleto(nombreMedicoCompleto);

        // Buscar médico
        Medico medico = medicoService
                .buscarPorNombresYApellidos(partes[0], partes[1])
                .orElse(null);

        if (medico == null) {
            return "No se encontró el médico " + nombreMedicoCompleto;
        }

        // Buscar citas
        List<CitaMedicaDTO> citas = citaService
                .listarCitasPorMedicoYFecha(
                        medico.getId(),
                        LocalDate.parse(fecha)
                );

        if (citas.isEmpty()) {
            return "El Dr. " + nombreMedicoCompleto + " no tiene citas ese día.";
        }

        // Formatear respuesta
        StringBuilder sb = new StringBuilder(
                "Citas del Dr. " + nombreMedicoCompleto + ":\n"
        );

        for (CitaMedicaDTO c : citas) {
            sb.append("- ")
                    .append(c.getHora())
                    .append(" | ")
                    .append(c.getPacienteNombreCompleto())
                    .append(" | ")
                    .append(c.getMotivo())
                    .append("\n");
        }

        return sb.toString();
    }

    /* =========================
       UTILIDAD PRIVADA
       ========================= */
    private String[] separarNombreCompleto(String nombreCompleto) {
        String[] partes = nombreCompleto.trim().split("\\s+");

        if (partes.length < 2) {
            throw new IllegalArgumentException(
                    "El nombre del médico debe incluir nombres y apellidos"
            );
        }

        String nombres = partes[0];
        String apellidos = String.join(
                " ",
                Arrays.copyOfRange(partes, 1, partes.length)
        );

        return new String[]{nombres, apellidos};
    }
}