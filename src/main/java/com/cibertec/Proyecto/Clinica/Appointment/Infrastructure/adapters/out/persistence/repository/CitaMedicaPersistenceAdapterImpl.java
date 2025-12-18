package com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.out.persistence.repository;

import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.in.rest.dto.CitaMedicaDTO;
import com.cibertec.Proyecto.Clinica.Appointment.domain.Model.CitaMedica;
import com.cibertec.Proyecto.Clinica.Appointment.application.ports.out.CitaMedicaPersistencePort;
import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.out.persistence.entity.CitaMedicaEntity;
import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.out.persistence.mapper.CitaMedicaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CitaMedicaPersistenceAdapterImpl implements CitaMedicaPersistencePort {

    private final CitaMedicaRepositoryJpa citaMedicaRepositoryJpa;
    private final CitaMedicaMapper citaMedicaMapper;

    @Override
    public CitaMedica save(CitaMedica citaMedica) {
        CitaMedicaEntity entity = citaMedicaMapper.toEntity(citaMedica);
        return citaMedicaMapper.toDomain(citaMedicaRepositoryJpa.save(entity));
    }

    @Override
    public Optional<CitaMedica> findById(Integer id) {
        return citaMedicaRepositoryJpa.findById(id).map(citaMedicaMapper::toDomain);
    }

    @Override
    public List<CitaMedica> findAll() {
        return citaMedicaRepositoryJpa.findAll()
                .stream()
                .map(citaMedicaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        citaMedicaRepositoryJpa.deleteById(id);
    }

    @Transactional
    @Override
    public CitaMedica update(CitaMedica citaMedica) {
        if (!citaMedicaRepositoryJpa.existsById(citaMedica.getId())) {
            throw new RuntimeException("Cita médica con ID " + citaMedica.getId() + " no encontrada");
        }
        CitaMedicaEntity entity = citaMedicaMapper.toEntity(citaMedica);
        return citaMedicaMapper.toDomain(citaMedicaRepositoryJpa.save(entity));
    }

    @Override
    public Page<CitaMedicaDTO> findAllPaginado(Pageable pageable) {
        return citaMedicaRepositoryJpa.findAllCitasConNombres(pageable);
    }
    @Override
    public List<CitaMedicaDTO> findByMedicoAndFecha(Integer medicoId, LocalDate fecha) {
        return citaMedicaRepositoryJpa.findCitasPorMedicoYFecha(medicoId, fecha);
    }
}
