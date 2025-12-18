package com.cibertec.Proyecto.Clinica.AI.Tools;


import com.cibertec.Proyecto.Clinica.Doctor.application.ports.in.EspecialidadServicePort;
import com.cibertec.Proyecto.Clinica.Doctor.application.ports.in.MedicoServicePort;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MedicoTools {

    private final MedicoServicePort medicoServicePort;
    private final EspecialidadServicePort especialidadServicePort;

    // Crear médico
    @Tool
    public Medico crearMedico(
            String nombres,
            String apellidos,
            String cmp,
            String especialidad
    ) {
        Especialidad esp = especialidadServicePort
                .buscarPorNombre(especialidad)
                .orElseThrow(null);

        Medico medico = new Medico();
        medico.setNombres(nombres);
        medico.setApellidos(apellidos);
        medico.setCmp(cmp);
        medico.setEspecialidad(esp);

        return medicoServicePort.guardar(medico);
    }


    //Actualizar médico
    @Tool
    public Medico actualizarMedico(
            String nombres,
            String apellidos,
            String cmp,
            String especialidad
    ) {
        Medico medico = medicoServicePort
                .buscarPorNombresYApellidos(nombres, apellidos)
                .orElseThrow(null);

        Especialidad esp = especialidadServicePort
                .buscarPorNombre(especialidad)
                .orElseThrow(null);

        medico.setNombres(nombres);
        medico.setCmp(cmp);
        medico.setApellidos(apellidos);
        medico.setEspecialidad(esp);

        return medicoServicePort.actualizar(medico);
    }



    @Tool
    public String medicosDisponiblesEnFecha(String fecha) {
        LocalDate f = LocalDate.parse(fecha);

        List<Medico> medicos = medicoServicePort.obtenerDisponiblesEnFecha(f);

        if (medicos.isEmpty()) {
            return "No hay médicos disponibles para la fecha " + fecha;
        }

        StringBuilder sb = new StringBuilder("Médicos disponibles:\n");

        for (Medico m : medicos) {
            sb.append("- ")
                    .append(m.getNombres())
                    .append(" ")
                    .append(m.getApellidos())
                    .append(" (")
                    .append(m.getEspecialidad().getNombre())
                    .append(")\n");
        }

        return sb.toString();
    }
    @Tool
    public String medicosDisponiblesPorEspecialidadYHora(
            String especialidad,
            String fecha,
            String horaDesde
    ) {
        LocalDate f = LocalDate.parse(fecha);
        LocalTime h = LocalTime.parse(horaDesde);

        List<Medico> medicos = medicoServicePort
                .obtenerDisponiblesPorEspecialidadYHora(
                        especialidad,
                        f,
                        h
                );

        if (medicos.isEmpty()) {
            return "No hay médicos de " + especialidad +
                    " disponibles el " + fecha +
                    " desde las " + horaDesde;
        }

        StringBuilder sb = new StringBuilder(
                "Médicos de " + especialidad +
                        " disponibles desde las " + horaDesde + ":\n"
        );

        for (Medico m : medicos) {
            sb.append("- Dr. ")
                    .append(m.getNombres())
                    .append(" ")
                    .append(m.getApellidos())
                    .append("\n");
        }

        return sb.toString();
    }


}