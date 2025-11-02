package com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.out.persistence.repository;

import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.out.persistence.entity.CitaMedicaEntity;
import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.out.persistence.mapper.CitaMedicaMapper;
import com.cibertec.Proyecto.Clinica.Appointment.domain.Model.CitaMedica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaMedicaPersistenceAdapterImplTest {
    @Mock
    private CitaMedicaRepositoryJpa repositoryJpa;

    @Mock
    private CitaMedicaMapper mapper;

    @InjectMocks
    private CitaMedicaPersistenceAdapterImpl adapter;

    private CitaMedica citaMedica;
    private CitaMedicaEntity entity;

    @BeforeEach
    void setUp() {
        citaMedica = new CitaMedica();
        citaMedica.setId(1);
        citaMedica.setMotivo("Consulta general");

        entity = new CitaMedicaEntity();
        entity.setId(1);
        entity.setMotivo("Consulta general");
    }

    @Test
    void testSave() {
        when(mapper.toEntity(citaMedica)).thenReturn(entity);
        when(repositoryJpa.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(citaMedica);

        CitaMedica result = adapter.save(citaMedica);

        assertEquals(citaMedica.getId(), result.getId());
        verify(repositoryJpa, times(1)).save(entity);
    }

    @Test
    void testFindById() {
        when(repositoryJpa.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(citaMedica);

        Optional<CitaMedica> result = adapter.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Consulta general", result.get().getMotivo());
    }

    @Test
    void testFindAll() {
        when(repositoryJpa.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(citaMedica);

        List<CitaMedica> result = adapter.findAll();

        assertEquals(1, result.size());
        assertEquals("Consulta general", result.get(0).getMotivo());
    }

    @Test
    void testDeleteById() {
        adapter.deleteById(1);
        verify(repositoryJpa, times(1)).deleteById(1);
    }

    @Test
    void testUpdate() {
        when(repositoryJpa.existsById(citaMedica.getId())).thenReturn(true);
        when(mapper.toEntity(citaMedica)).thenReturn(entity);
        when(repositoryJpa.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(citaMedica);

        CitaMedica result = adapter.update(citaMedica);

        assertEquals("Consulta general", result.getMotivo());
    }

    @Test
    void testFindAllPaginado() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page pageMock = new PageImpl(List.of());

        when(repositoryJpa.findAllCitasConNombres(pageable)).thenReturn(pageMock);

        Page result = adapter.findAllPaginado(pageable);

        assertNotNull(result);
        verify(repositoryJpa, times(1)).findAllCitasConNombres(pageable);
    }
    @Test
    void testUpdateThrowsExceptionWhenCitaNotFound() {
        // We simulate that the ID does not exist in the database
        when(repositoryJpa.existsById(citaMedica.getId())).thenReturn(false);

        // We verified that the expected exception was thrown.
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adapter.update(citaMedica);
        });

        // We validate the error message
        assertEquals("Cita médica con ID " + citaMedica.getId() + " no encontrada", exception.getMessage());

        // We verified that no attempt was made to save anything.
        verify(repositoryJpa, never()).save(any());
    }

}