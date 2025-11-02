package com.cibertec.Proyecto.Clinica.Doctor.application.services;

import com.cibertec.Proyecto.Clinica.Doctor.application.ports.out.EspecialidadPersistence;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class EspecialidadServiceimplTest {
    @Mock
    private EspecialidadPersistence persistence;
    @InjectMocks
    private EspecialidadServiceimpl service;

    private Especialidad especialidad;
    @BeforeEach
    void setUp(){
        especialidad=new Especialidad();
        especialidad.setId(1);
        especialidad.setNombre("Cardiología");
        especialidad.setDescripcion("Especialidad del corazón");
    }
    @Test
    void testGuardar(){
        when(persistence.save(especialidad)).thenReturn(especialidad);
        Especialidad result=service.guardar(especialidad);
        assertEquals("Cardiología",result.getNombre());
        verify(persistence).save(especialidad);
    }

    @Test
    void testObtenerPorId(){
        when(persistence.findById(1)).thenReturn(Optional.of(especialidad));
        Especialidad result= service.obtenerPorId(1);
        assertEquals("Cardiología",result.getNombre());
        verify(persistence).findById(1);
    }

    @Test
    void testObtenerPorIdError(){
        int id=99;
        when(persistence.findById(id)).thenReturn(Optional.empty());
        RuntimeException ex=assertThrows(RuntimeException.class,()->service.obtenerPorId(id));
        assertTrue(ex.getMessage().contains("Especialidad no Encontrado"));
    }

    @Test
    void testListar(){
        when(persistence.findAll()).thenReturn(Arrays.asList(especialidad));
        List<Especialidad> result=service.listar();
        assertEquals(1,result.size());
        assertEquals("Cardiología",result.get(0).getNombre());
        verify(persistence,times(1)).findAll();
    }

    @Test
    void testActualizar(){
        int id=1;
        when(persistence.actualizar(id,especialidad)).thenReturn(especialidad);
        Especialidad result=service.actualizar(id,especialidad);
        assertEquals("Cardiología",result.getNombre());
        verify(persistence).actualizar(id,especialidad);
    }

    @Test
    void testEliminar(){
        service.eliminar(1);
        verify(persistence).deleteById(1);
    }


    @Test
    void testListarPaginado(){
        Page<Especialidad> page= new PageImpl<>(List.of(especialidad));
        when(persistence.findAllPaginado(0,10)).thenReturn(page);
        Page<Especialidad> result= service.listarPaginado(0,10);
        assertEquals(1,result.getContent().size());
        verify(persistence).findAllPaginado(0,10);
    }
}