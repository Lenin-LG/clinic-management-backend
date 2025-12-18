package com.cibertec.Proyecto.Clinica.AI.Tools;

import com.cibertec.Proyecto.Clinica.Patient.application.ports.in.PacienteService;
import com.cibertec.Proyecto.Clinica.Patient.domain.Model.Paciente;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PacienteTools {

    private final PacienteService pacienteService;

    @Tool
    public Paciente crearPaciente(
            String nombres,
            String apellidos,
            String dni,
            String fechaNacimiento,
            String direccion,
            String telefono,
            String email
    ) {
        Paciente p = new Paciente();
        p.setNombres(nombres);
        p.setApellidos(apellidos);
        p.setDni(dni);
        p.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
        p.setDireccion(direccion);
        p.setTelefono(telefono);
        p.setEmail(email);
        return pacienteService.agregar(p);
    }



}
