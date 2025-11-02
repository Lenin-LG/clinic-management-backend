package com.cibertec.Proyecto.Clinica.Patient.Infrastructure.adapters.out.persistence.repository;

import com.cibertec.Proyecto.Clinica.Patient.Infrastructure.adapters.out.persistence.entity.PacienteEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("h2-test")
class PacienteRepositoryJpaTest {
    @Autowired
    private PacienteRepositoryJpa repository;
    @Autowired
    private EntityManager entityManager;

    private PacienteEntity pacienteGuardado;

    @BeforeEach
    void setUp(){
        PacienteEntity paciente = new PacienteEntity();
        paciente.setNombres("Juan");
        paciente.setApellidos("Perez");
        paciente.setFechaNacimiento(LocalDate.of(1990, 5, 10));
        paciente.setDni("12345678");
        paciente.setDireccion("Av. Lima 123");
        paciente.setTelefono("987654321");
        paciente.setEmail("juan.perez@gmail.com");
        paciente.setUsuarioActualizacion("admin");
        paciente.setFechaActualizacion(LocalDate.now());
        pacienteGuardado = repository.save(paciente);
    }

    @Test
    void testActualizarPaciente(){
        int filasActualizadas = repository.actualizarPaciente(
                pacienteGuardado.getId(),
                "Carlos",
                "Gonzales",
                LocalDate.of(1992, 1, 1),
                "87654321",
                "Calle Sol 456",
                "999888777",
                "carlos.gonzales@gmail.com",
                "tester"
        );
        entityManager.flush();
        entityManager.clear();
        assertEquals(1, filasActualizadas);

        PacienteEntity actualizado = repository.findById(pacienteGuardado.getId()).orElseThrow();

        assertEquals("Carlos", actualizado.getNombres());
        assertEquals("Gonzales", actualizado.getApellidos());
        assertEquals("87654321", actualizado.getDni());
        assertEquals("Calle Sol 456", actualizado.getDireccion());
        assertEquals("999888777", actualizado.getTelefono());
        assertEquals("carlos.gonzales@gmail.com", actualizado.getEmail());
        assertEquals("tester", actualizado.getUsuarioActualizacion());
        assertNotNull(actualizado.getFechaActualizacion());
    }

    @Test
    void testFindAllPaginado() {
        PacienteEntity paciente2 = new PacienteEntity();
        paciente2.setNombres("Ana");
        paciente2.setApellidos("Alvarez");
        paciente2.setFechaNacimiento(LocalDate.of(1995, 7, 20));
        paciente2.setDni("55555555");
        paciente2.setDireccion("Av. Arequipa 456");
        paciente2.setTelefono("987654322");
        paciente2.setEmail("ana.alvarez@gmail.com");
        paciente2.setUsuarioActualizacion("admin");
        paciente2.setFechaActualizacion(LocalDate.now());
        repository.save(paciente2);

        Page<PacienteEntity> page = repository.findAllPaginado(PageRequest.of(0, 10));

        assertEquals(2, page.getTotalElements());

        PacienteEntity primero = page.getContent().get(0);
        PacienteEntity segundo = page.getContent().get(1);
        assertTrue(primero.getApellidos().compareTo(segundo.getApellidos()) < 0);
    }


}