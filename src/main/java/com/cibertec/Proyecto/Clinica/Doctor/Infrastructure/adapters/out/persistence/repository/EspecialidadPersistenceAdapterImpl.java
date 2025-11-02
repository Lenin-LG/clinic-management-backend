package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.repository;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import com.cibertec.Proyecto.Clinica.Doctor.application.ports.out.EspecialidadPersistence;
import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.EspecialidadEntity;
import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.mapper.EspecialidadMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EspecialidadPersistenceAdapterImpl implements EspecialidadPersistence {

    private final EspecialidadRepositoryJpa especialidadRepositoryJpa;
    private final EspecialidadMapper especialidadMapper;
    private final EntityManager entityManager;
    @Override
    public Especialidad save(Especialidad especialidad) {
        EspecialidadEntity entity = especialidadMapper.toEntity(especialidad);
        return especialidadMapper.toDomain(especialidadRepositoryJpa.save(entity));
    }

    @Override
    public Optional<Especialidad> findById(Integer id) {
        return especialidadRepositoryJpa.findById(id)
                .map(especialidadMapper::toDomain);
    }

    @Override
    public List<Especialidad> findAll() {
        return especialidadRepositoryJpa.findAll()
                .stream()
                .map(especialidadMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Especialidad actualizar(Integer id, Especialidad especialidad) {
        int rows = especialidadRepositoryJpa.actualizarEspecialidad(id, especialidad.getNombre(), especialidad.getDescripcion());
        if (rows == 0) {
            throw new RuntimeException("Especialidad con ID " + id + " no encontrada");
        }
        entityManager.clear();
        //I'll check again to return the updated version
        return especialidadRepositoryJpa.findById(id)
                .map(especialidadMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Especialidad con ID " + id + " no encontrada tras actualizar"));
    }

    @Override
    public void deleteById(Integer id) {
        especialidadRepositoryJpa.deleteById(id);
    }

    @Override
    public Page<Especialidad> findAllPaginado(int page, int size) {
        return especialidadRepositoryJpa.findAllPaginado(PageRequest.of(page, size))
                .map(especialidadMapper::toDomain);
    }
}