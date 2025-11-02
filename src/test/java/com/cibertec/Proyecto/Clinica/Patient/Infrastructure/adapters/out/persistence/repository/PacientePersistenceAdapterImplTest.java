package com.cibertec.Proyecto.Clinica.Patient.Infrastructure.adapters.out.persistence.repository;

import com.cibertec.Proyecto.Clinica.Patient.Infrastructure.adapters.out.persistence.mapper.PacienteMapperImpl;
import com.cibertec.Proyecto.Clinica.Patient.domain.Model.Paciente;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({PacientePersistenceAdapterImpl.class, PacienteMapperImpl.class})
@ActiveProfiles("h2-test")
class PacientePersistenceAdapterImplTest {
    @Autowired
    private PacientePersistenceAdapterImpl adapter;

    @Autowired
    private PacienteRepositoryJpa repository;
    private Paciente paciente;
    @Autowired
    private EntityManager entityManager;
    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setNombres("Juan");
        paciente.setApellidos("Perez");
        paciente.setDni("12345678");
        paciente.setFechaNacimiento(LocalDate.of(1990, 5, 10));
        paciente.setDireccion("Av. Lima 123");
        paciente.setTelefono("987654321");
        paciente.setEmail("juan.perez@gmail.com");
    }

    @Test
    void testSaveAndFindById() {
        Paciente guardado = adapter.save(paciente);
        assertNotNull(guardado.getId());

        Optional<Paciente> encontrado = adapter.findById(guardado.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Juan", encontrado.get().getNombres());
    }
    @Test
    void testFindAll() {
        Paciente p2 = new Paciente();
        p2.setNombres("Ana");
        p2.setApellidos("Alvarez");
        p2.setDni("55555555");

        adapter.save(paciente);
        adapter.save(p2);

        List<Paciente> todos = adapter.findAll();
        assertEquals(2, todos.size());
        assertTrue(todos.stream().anyMatch(p -> p.getNombres().equals("Juan")));
        assertTrue(todos.stream().anyMatch(p -> p.getNombres().equals("Ana")));
    }
    @Test
    void testUpdate() {
        Paciente guardado = adapter.save(paciente);
        guardado.setNombres("Carlos");
        guardado.setApellidos("Gonzales");
        guardado.setDni("87654321");

        Paciente actualizado = adapter.update(guardado);
        entityManager.flush();
        entityManager.clear();
        assertEquals("Carlos", actualizado.getNombres());
        assertEquals("Gonzales", actualizado.getApellidos());

        Optional<Paciente> desdeDb = adapter.findById(guardado.getId());
        assertTrue(desdeDb.isPresent());
        assertEquals("Carlos", desdeDb.get().getNombres());
    }
    @Test
    void testUpdateNoEncontrado() {
        paciente.setId(999);
        assertThrows(RuntimeException.class, () -> adapter.update(paciente));
    }
    @Test
    void testDeleteById() {
        Paciente guardado = adapter.save(paciente);
        adapter.deleteById(guardado.getId());
        Optional<Paciente> desdeDb = adapter.findById(guardado.getId());
        assertFalse(desdeDb.isPresent());
    }
    @Test
    void testFindAllPaginado() {
        Paciente p2 = new Paciente();
        p2.setNombres("Ana");
        p2.setApellidos("Alvarez");
        p2.setDni("55555555");
        adapter.save(paciente);
        adapter.save(p2);

        Page<Paciente> page = adapter.findAllPaginado(PageRequest.of(0, 10));
        assertEquals(2, page.getTotalElements());
        assertEquals("Alvarez", page.getContent().get(0).getApellidos());
    }

}