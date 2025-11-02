package com.cibertec.Proyecto.Clinica.Doctor.application.services;

import com.cibertec.Proyecto.Clinica.Doctor.application.ports.out.MedicoPersistence;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MedicoServiceImplTest {
    @Mock
    private MedicoPersistence persistence;
    @InjectMocks
    private MedicoServiceImpl service;

    private Medico medico;
    private Especialidad especialidad;

    @BeforeEach
    void setUp(){
        especialidad=new Especialidad();
        especialidad.setId(1);
        especialidad.setNombre("Cardiología");
        especialidad.setDescripcion("Especialidad del corazón");
        medico=new Medico();
        medico.setId(1);
        medico.setCmp("CMP1234");
        medico.setApellidos("Laura Garcia");
        medico.setNombres("Lenin Antony");
        medico.setEspecialidad(especialidad);
    }
    @Test
    void testGuardar(){
        when(persistence.save(medico)).thenReturn(medico);
        Medico result=service.guardar(medico);
        assertEquals("Lenin Antony",result.getNombres());
        verify(persistence).save(medico);
    }

    @Test
    void testObtenerPorId(){
        when(persistence.findById(1)).thenReturn(Optional.of(medico));
        Medico result= service.obtenerPorId(1);
        assertEquals("Lenin Antony",result.getNombres());
        verify(persistence).findById(1);
    }

    @Test
    void testObtenerPorIdError(){
        int id=99;
        when(persistence.findById(id)).thenReturn(Optional.empty());
        RuntimeException ex=assertThrows(RuntimeException.class,()->service.obtenerPorId(id));
        assertTrue(ex.getMessage().contains("Médico no encontrado con id: " + id));
    }

    @Test
    void testListar(){
        when(persistence.findAll()).thenReturn(Arrays.asList(medico));
        List<Medico> result=service.listar();
        assertEquals(1,result.size());
        assertEquals("Lenin Antony",result.get(0).getNombres());
        verify(persistence,times(1)).findAll();
    }

    @Test
    void testActualizar(){
        when(persistence.update(medico)).thenReturn(medico);
        Medico result=service.actualizar(medico);
        assertEquals("Lenin Antony",result.getNombres());
        verify(persistence).update(medico);
    }

    @Test
    void testEliminar(){
        when(persistence.findById(1)).thenReturn(Optional.of(medico));
        service.eliminar(1);
        verify(persistence).deleteById(1);
    }

    @Test
    void testEliminarNoFound(){
        int id=1;
        when(persistence.findById(id)).thenReturn(Optional.empty());
        RuntimeException ex=assertThrows(RuntimeException.class,()->service.eliminar(id));
        assertTrue(ex.getMessage().contains("Medico no encontrado con id: " + id));
    }

    @Test
    void testListarPaginado(){
        Page<Medico> page= new PageImpl<>(List.of(medico));
        when(persistence.listarPaginado(PageRequest.of(0,10,Sort.by("apellidos").ascending()))).thenReturn(page);
        Page<Medico> result= service.listarPaginado(0,10);
        assertEquals(1,result.getContent().size());
        verify(persistence).listarPaginado(PageRequest.of(0,10,Sort.by("apellidos").ascending()));
    }



















}