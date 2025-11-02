package com.cibertec.Proyecto.Clinica.Patient.application.ports.out;

import com.cibertec.Proyecto.Clinica.Patient.domain.Model.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PacientePersistence
{
    Paciente save(Paciente paciente);
    Optional<Paciente> findById(Integer id);
    List<Paciente> findAll();
    @Transactional
    Paciente update(Paciente paciente);
    void deleteById(Integer id);
    Page<Paciente> findAllPaginado(Pageable pageable);
}
