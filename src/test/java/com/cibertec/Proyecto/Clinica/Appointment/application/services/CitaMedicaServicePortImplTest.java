package com.cibertec.Proyecto.Clinica.Appointment.application.services;

import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.in.rest.dto.CitaMedicaDTO;
import com.cibertec.Proyecto.Clinica.Appointment.application.ports.out.CitaMedicaPersistencePort;
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
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaMedicaServicePortImplTest {
    @Mock
    private CitaMedicaPersistencePort citaMedicaPersistencePort;

    @InjectMocks
    private CitaMedicaServicePortImpl service;

    private CitaMedica citaMedica;

    @BeforeEach
    void setUp() {
        citaMedica = new CitaMedica();
        citaMedica.setId(1);
        citaMedica.setMotivo("Chequeo general");
    }

    @Test
    void registerAppoinmentTest() {
        // --- Given ---
        when(citaMedicaPersistencePort.save(any(CitaMedica.class))).thenAnswer(invocation -> {
            CitaMedica argument = invocation.getArgument(0);
            argument.setId(1);
            return argument;
        });

        // --- When ---
        CitaMedica resultado = service.registrarCita(citaMedica);

        // --- Then ---
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("admin", resultado.getUsuarioCreacion());
        assertNotNull(resultado.getFechaCreacion());
        verify(citaMedicaPersistencePort).save(any(CitaMedica.class));
    }

    @Test
    void getByIdTest() {
        // --- Given ---
        when(citaMedicaPersistencePort.findById(1)).thenReturn(Optional.of(citaMedica));

        // --- When ---
        Optional<CitaMedica> resultado = service.obtenerCitaPorId(1);

        // --- Then ---
        assertTrue(resultado.isPresent());
        assertEquals("Chequeo general", resultado.get().getMotivo());
        verify(citaMedicaPersistencePort).findById(1);
    }

    @Test
    void deleteAppoinmentTest() {
        // --- Given ---
        when(citaMedicaPersistencePort.findById(1)).thenReturn(Optional.of(citaMedica));

        // --- When ---
        service.eliminarCita(1);

        // --- Then ---
        verify(citaMedicaPersistencePort).deleteById(1);
    }

    @Test
    void deleteAppoinmentExceptionTest() {
        // --- Given ---
        when(citaMedicaPersistencePort.findById(1)).thenReturn(Optional.empty());

        // --- When & Then ---
        assertThrows(RuntimeException.class, () -> service.eliminarCita(1));
        verify(citaMedicaPersistencePort, never()).deleteById(any());
    }

    @Test
    void updateAppoinmentTest() {
        // --- Given ---
        when(citaMedicaPersistencePort.update(any(CitaMedica.class))).thenReturn(citaMedica);

        // --- When ---
        CitaMedica resultado = service.actualizarCita(citaMedica);

        // --- Then ---
        assertNotNull(resultado);
        assertEquals("admin_update", resultado.getUsuarioActualizacion());
        assertNotNull(resultado.getFechaActualizacion());
        verify(citaMedicaPersistencePort).update(any(CitaMedica.class));
    }

    @Test
    void listAppoinmentTest() {
        // --- Given ---
        Pageable pageable = PageRequest.of(0, 5);
        Page<CitaMedicaDTO> pageMock = new PageImpl<>(List.of(new CitaMedicaDTO()));
        when(citaMedicaPersistencePort.findAllPaginado(pageable)).thenReturn(pageMock);

        // --- When ---
        Page<CitaMedicaDTO> resultado = service.listarCitasPaginadas(pageable);

        // --- Then ---
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(citaMedicaPersistencePort).findAllPaginado(pageable);
    }
}