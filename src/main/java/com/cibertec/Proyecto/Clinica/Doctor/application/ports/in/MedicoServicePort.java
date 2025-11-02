package com.cibertec.Proyecto.Clinica.Doctor.application.ports.in;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MedicoServicePort {
    List<Medico> listar();
    Medico obtenerPorId(Integer id);
    Medico guardar(Medico medico);
    Medico actualizar(Medico medico);
    void eliminar(Integer id);
    Page<Medico> listarPaginado(int page, int size);

}
