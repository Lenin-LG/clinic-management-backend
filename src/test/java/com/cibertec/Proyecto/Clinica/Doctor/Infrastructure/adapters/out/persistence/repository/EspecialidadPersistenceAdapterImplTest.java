package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.repository;

import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.EspecialidadEntity;
import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.mapper.EspecialidadMapperImpl;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import com.cibertec.Proyecto.Clinica.Patient.Infrastructure.adapters.out.persistence.mapper.PacienteMapperImpl;
import com.cibertec.Proyecto.Clinica.Patient.Infrastructure.adapters.out.persistence.repository.PacientePersistenceAdapterImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Import({EspecialidadPersistenceAdapterImpl.class, EspecialidadMapperImpl.class})
@ActiveProfiles("h2-test")
class EspecialidadPersistenceAdapterImplTest {

    @Autowired
    private EspecialidadPersistenceAdapterImpl  adapter;

    @Autowired
    private EspecialidadRepositoryJpa repository;

    private Especialidad especialidad;

    @Autowired
    private EntityManager entityManager;
    @BeforeEach
    void setUp() {
        especialidad = new Especialidad();
        especialidad.setNombre("Cardiología");
        especialidad.setDescripcion("Especialidad del corazón");
    }
    @Test
    void testSave() {
        Especialidad guardada = adapter.save(especialidad);

        assertNotNull(guardada.getId());
        assertEquals("Cardiología", guardada.getNombre());
        assertEquals("Especialidad del corazón", guardada.getDescripcion());
    }
    @Test
    void testFindById() {
        Especialidad guardada = adapter.save(especialidad);

        Optional<Especialidad> encontrada = adapter.findById(guardada.getId());

        assertTrue(encontrada.isPresent());
        assertEquals("Cardiología", encontrada.get().getNombre());
    }

    @Test
    void testFindAll() {
        adapter.save(especialidad);

        List<Especialidad> lista = adapter.findAll();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
    }

    @Test
    void testActualizar() {
        Especialidad guardada = adapter.save(especialidad);

        Especialidad actualizada = new Especialidad();
        actualizada.setNombre("Neurología");
        actualizada.setDescripcion("Sistema nervioso");

        Especialidad resultado = adapter.actualizar(guardada.getId(), actualizada);

        assertEquals("Neurología", resultado.getNombre());
        assertEquals("Sistema nervioso", resultado.getDescripcion());
    }

    @Test
    void testActualizarEspecialidadNoExistente() {
        Especialidad nueva = new Especialidad();
        nueva.setNombre("Pediatría");
        nueva.setDescripcion("Niños");

        assertThrows(RuntimeException.class, () ->
                adapter.actualizar(999, nueva)
        );
    }

    @Test
    void testFindAllPaginado() {
        adapter.save(especialidad);

        Page<Especialidad> page = adapter.findAllPaginado(0, 10);

        assertNotNull(page);
        assertFalse(page.isEmpty());
        assertEquals("Cardiología", page.getContent().get(0).getNombre());
    }

    @Test
    void testDeleteById() {
        Especialidad guardada = adapter.save(especialidad);
        adapter.deleteById(guardada.getId());

        Optional<EspecialidadEntity> encontrada = repository.findById(guardada.getId());
        assertTrue(encontrada.isEmpty());
    }
}