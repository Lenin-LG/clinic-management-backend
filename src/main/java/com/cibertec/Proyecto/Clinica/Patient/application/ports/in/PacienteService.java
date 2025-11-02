package com.cibertec.Proyecto.Clinica.Patient.application.ports.in;

import com.cibertec.Proyecto.Clinica.Patient.domain.Model.Paciente;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PacienteService {
    List<Paciente> listar();
    Paciente obtener(Integer id);
    Paciente agregar(Paciente paciente);
    Paciente actualizar(Paciente paciente);
    void eliminar(Integer id);
    Page<Paciente> listarPaginado(int page, int size);
}
