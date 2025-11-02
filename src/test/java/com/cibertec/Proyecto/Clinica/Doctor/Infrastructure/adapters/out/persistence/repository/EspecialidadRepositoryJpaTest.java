package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.repository;

import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.EspecialidadEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("h2-test")
class EspecialidadRepositoryJpaTest {
    @Autowired
    private EspecialidadRepositoryJpa repository;

    @Autowired
    private EntityManager entityManager;

    private EspecialidadEntity especialidadGuardada;

    @BeforeEach
    void setUp() {
        //Create and save an initial specialty
        EspecialidadEntity especialidad = new EspecialidadEntity();
        especialidad.setNombre("Cardiología");
        especialidad.setDescripcion("Especialidad del corazón");
        especialidadGuardada = repository.save(especialidad);
    }

    @Test
    void testActualizarEspecialidad() {
        // Run the custom update query
        int filasActualizadas = repository.actualizarEspecialidad(
                especialidadGuardada.getId(),
                "Neurología",
                "Trata el sistema nervioso"
        );

        assertEquals(1, filasActualizadas);

        // Force synchronization and reload from the database
        entityManager.flush();
        entityManager.clear();

        EspecialidadEntity actualizada = repository.findById(especialidadGuardada.getId())
                .orElseThrow();

        assertEquals("Neurología", actualizada.getNombre());
        assertEquals("Trata el sistema nervioso", actualizada.getDescripcion());
    }

    @Test
    void testFindAllPaginado() {
        // Add more specialties to test pagination and order
        EspecialidadEntity e2 = new EspecialidadEntity();
        e2.setNombre("Dermatología");
        e2.setDescripcion("Trata enfermedades de la piel");
        repository.save(e2);

        EspecialidadEntity e3 = new EspecialidadEntity();
        e3.setNombre("Anestesiología");
        e3.setDescripcion("Control del dolor y anestesia");
        repository.save(e3);

        entityManager.flush();
        entityManager.clear();

        Pageable pageable = PageRequest.of(0, 10);
        Page<EspecialidadEntity> page = repository.findAllPaginado(pageable);

        assertNotNull(page);
        assertFalse(page.isEmpty());
        assertEquals(3, page.getTotalElements());

        // Verify that the order is alphabetical (ASC by name)
        List<String> nombres = page.getContent()
                .stream()
                .map(EspecialidadEntity::getNombre)
                .toList();

        assertEquals(List.of("Anestesiología", "Cardiología", "Dermatología"), nombres);
    }
}