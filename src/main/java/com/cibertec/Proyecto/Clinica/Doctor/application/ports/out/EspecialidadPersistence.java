package com.cibertec.Proyecto.Clinica.Doctor.application.ports.out;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EspecialidadPersistence {
    Especialidad save(Especialidad especialidad);
    Optional<Especialidad> findById(Integer id);
    List<Especialidad> findAll();
    Especialidad actualizar(Integer id, Especialidad especialidad);
    void deleteById(Integer id);
    Page<Especialidad> findAllPaginado(int page, int size);
}
