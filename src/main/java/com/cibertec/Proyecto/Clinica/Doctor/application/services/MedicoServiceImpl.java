package com.cibertec.Proyecto.Clinica.Doctor.application.services;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import com.cibertec.Proyecto.Clinica.Doctor.application.ports.out.MedicoPersistence;
import com.cibertec.Proyecto.Clinica.Doctor.application.ports.in.MedicoServicePort;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoServicePort {

    private final MedicoPersistence medicoPersistence;

    @Override
    public Medico guardar(Medico medico) {
        return medicoPersistence.save(medico);
    }

    @Override
    public Medico obtenerPorId(Integer id) {
        return medicoPersistence.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con id: " + id));
    }

    @Override
    public List<Medico> listar() {
        return medicoPersistence.findAll();
    }

    @Override
    public Medico actualizar(Medico medico) {
        return  medicoPersistence.update(medico);
    }

    @Override
    public void eliminar(Integer id) {
        Medico medico = medicoPersistence.findById(id)
                .orElseThrow(() -> new RuntimeException("Medico no encontrado con id: " + id));
        medicoPersistence.deleteById(id);
    }

    @Override
    public Page<Medico> listarPaginado(int page, int size) {
        return medicoPersistence.listarPaginado(PageRequest.of(page, size, Sort.by("apellidos").ascending()));
    }

    @Override
    public List<Medico> obtenerDisponiblesEnFecha(LocalDate fecha) {
        return medicoPersistence.findDisponiblesEnFecha(fecha);
    }

    @Override
    public Optional<Medico> buscarPorNombresYApellidos(String nombres, String apellidos) {
        return medicoPersistence.findByNombresYApellidos(nombres, apellidos);
    }
    @Override
    public List<Medico> obtenerDisponiblesPorEspecialidadYHora(
            String especialidad,
            LocalDate fecha,
            LocalTime horaDesde
    ) {
        return medicoPersistence
                .findDisponiblesPorEspecialidadYHora(
                        especialidad,
                        fecha,
                        horaDesde
                );
    }



}
