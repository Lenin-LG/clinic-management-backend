package com.cibertec.Proyecto.Clinica.Patient.application.services;

import com.cibertec.Proyecto.Clinica.Patient.application.ports.out.PacientePersistence;
import com.cibertec.Proyecto.Clinica.Patient.domain.Model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PacienteServiceImplTest {

    @Mock
    private PacientePersistence repository;

    @InjectMocks
    private PacienteServiceImpl service;

    private Paciente paciente;

    @BeforeEach
    void setUp(){
        paciente =new Paciente();
        paciente.setId(1);
        paciente.setNombres("Lenin Laura");
    }

    @Test
    void testListar(){
            when(repository.findAll()).thenReturn(Arrays.asList(paciente));
            List<Paciente> result= service.listar();
            assertEquals(1,result.size());
            assertEquals("Lenin Laura",result.get(0).getNombres());
            verify(repository,times(1)).findAll();
    }
    @Test
    void testObtenerNoExiste(){
            when(repository.findById(99)).thenReturn(Optional.empty());
            RuntimeException ex= assertThrows(RuntimeException.class,()->service.obtener(99));
            assertTrue(ex.getMessage().contains("Paciente no Encontrado"));
            verify(repository).findById(99);
    }
    @Test
    void testObtener(){
            when(repository.findById(1)).thenReturn(Optional.of(paciente));

            Paciente result=service.obtener(1);

            assertEquals("Lenin Laura",result.getNombres());

            verify(repository).findById(1);
    }
    @Test
    void testAgregar(){
            when(repository.save(paciente)).thenReturn(paciente);
            Paciente result=service.agregar(paciente);
            assertNotNull(result);
            assertEquals("Lenin Laura",result.getNombres());
            verify(repository).save(paciente);
    }
    @Test
    void testActualizar(){
            when(repository.update(paciente)).thenReturn(paciente);
            Paciente result=service.actualizar(paciente);
            assertEquals("Lenin Laura",result.getNombres());
            verify(repository).update(paciente);
    }
    @Test
    void testEliminar(){
            when(repository.findById(1)).thenReturn(Optional.of(paciente));
            service.eliminar(1);
            verify(repository).deleteById(1);
    }
    @Test
    void testEliminarNoExiste(){
            when(repository.findById(1)).thenReturn(Optional.empty());
            RuntimeException ex=assertThrows(RuntimeException.class,()->service.eliminar(1));
            assertTrue(ex.getMessage().contains("no encontrado"));
            verify(repository, never()).deleteById(any());
    }
    @Test
    void testListarPaginado(){
        Page<Paciente>  page= new PageImpl<>(List.of(paciente));
        when(repository.findAllPaginado(PageRequest.of(0,10))).thenReturn(page);
        Page<Paciente>  result=service.listarPaginado(0,10);
        assertEquals(1,result.getContent().size());
        verify(repository).findAllPaginado(PageRequest.of(0,10));
    }



}