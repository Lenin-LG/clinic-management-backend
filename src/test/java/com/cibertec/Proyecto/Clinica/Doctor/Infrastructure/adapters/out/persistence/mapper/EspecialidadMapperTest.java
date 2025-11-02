package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.mapper;

import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.EspecialidadEntity;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
class EspecialidadMapperTest {

    private final EspecialidadMapper mapper= Mappers.getMapper(EspecialidadMapper.class);

    @Test
    void testToDomain(){
        EspecialidadEntity entity=new EspecialidadEntity();
        entity.setId(1);
        entity.setNombre("Especialdiad Test");
        entity.setDescripcion("Description Test");

        Especialidad domain=mapper.toDomain(entity);
        assertEquals(entity.getId(),domain.getId());
        assertEquals(entity.getNombre(),domain.getNombre());
        assertEquals(entity.getDescripcion(),domain.getDescripcion());
    }
    @Test
    void testToEntity(){
        Especialidad domain=new Especialidad();
        domain.setId(1);
        domain.setNombre("Especialdiad Test");
        domain.setDescripcion("Description Test");

        EspecialidadEntity entity=mapper.toEntity(domain);

        assertEquals(domain.getId(),entity.getId());
        assertEquals(domain.getNombre(),entity.getNombre());
        assertEquals(domain.getDescripcion(),entity.getDescripcion());
    }

}