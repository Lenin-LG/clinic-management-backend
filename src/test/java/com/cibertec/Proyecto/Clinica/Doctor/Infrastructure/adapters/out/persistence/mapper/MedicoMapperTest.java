package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.mapper;

import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.EspecialidadEntity;
import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.MedicoEntity;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MedicoMapperImpl.class, EspecialidadMapperImpl.class})
class MedicoMapperTest {

    @Autowired
    private MedicoMapper mapper; //It is automatically injected along with Specialty Mapper

    @Test
    void testToDomain() {
        EspecialidadEntity especialidadEntity = new EspecialidadEntity();
        especialidadEntity.setId(1);
        especialidadEntity.setNombre("Cardiología");
        especialidadEntity.setDescripcion("Description Test");
        MedicoEntity entity = new MedicoEntity();
        entity.setId(1);
        entity.setNombres("José");
        entity.setApellidos("Martínez López");
        entity.setCmp("CMP1234");
        entity.setEspecialidad(especialidadEntity);
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setUsuarioCreacion("admin");

        Medico domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals("José", domain.getNombres());
        assertEquals("Martínez López", domain.getApellidos());
        assertEquals("CMP1234", domain.getCmp());
        assertNotNull(domain.getEspecialidad());
        assertEquals(1, domain.getEspecialidad().getId());
        assertEquals("Cardiología", domain.getEspecialidad().getNombre());
    }

    @Test
    void testToEntity() {
        Especialidad especialidad = new Especialidad();
        especialidad.setId(1);
        especialidad.setNombre("Cardiología");
        especialidad.setDescripcion("Description Test");
        Medico domain = new Medico();
        domain.setId(1);
        domain.setNombres("José");
        domain.setApellidos("Martínez López");
        domain.setCmp("CMP1234");
        domain.setEspecialidad(especialidad);
        domain.setFechaCreacion(LocalDateTime.now());
        domain.setUsuarioCreacion("admin");

        MedicoEntity entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals("José", entity.getNombres());
        assertEquals("Martínez López", entity.getApellidos());
        assertEquals("CMP1234", entity.getCmp());
        assertNotNull(entity.getEspecialidad());
        assertEquals(1, entity.getEspecialidad().getId());
        assertEquals("Cardiología", entity.getEspecialidad().getNombre());
    }
    @Test
    void testToDomain_NullEntity_ReturnsNull() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void testToEntity_NullModel_ReturnsNull() {
        assertNull(mapper.toEntity(null));
    }
}