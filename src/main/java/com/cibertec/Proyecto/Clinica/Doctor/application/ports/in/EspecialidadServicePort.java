package com.cibertec.Proyecto.Clinica.Doctor.application.ports.in;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EspecialidadServicePort {
    List<Especialidad> listar();
    Especialidad guardar(Especialidad especialidad);
    Especialidad obtenerPorId(Integer id);
    Especialidad actualizar(Integer id, Especialidad especialidad);
    void eliminar(Integer id);
    Page<Especialidad> listarPaginado(int page, int size);
}
