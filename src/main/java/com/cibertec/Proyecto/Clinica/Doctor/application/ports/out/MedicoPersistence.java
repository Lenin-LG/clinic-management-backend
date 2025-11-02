package com.cibertec.Proyecto.Clinica.Doctor.application.ports.out;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MedicoPersistence {
    Medico save(Medico medico);
    Optional<Medico> findById(Integer id);
    List<Medico> findAll();
    void deleteById(Integer id);
    @Transactional
    Medico update(Medico medico);
    Page<Medico> listarPaginado(Pageable pageable);
}
