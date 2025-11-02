package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.mapper;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.MedicoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EspecialidadMapper.class})
public interface MedicoMapper {

    MedicoMapper INSTANCE =  Mappers.getMapper(MedicoMapper.class);

    // Entity -> Domain
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombres", target = "nombres")
    @Mapping(source = "apellidos", target = "apellidos")
    @Mapping(source = "cmp", target = "cmp")
    @Mapping(source = "especialidad", target = "especialidad") // usa EspecialidadMapper
    @Mapping(source = "fechaCreacion", target = "fechaCreacion")
    @Mapping(source = "usuarioCreacion", target = "usuarioCreacion")
    @Mapping(source = "fechaActualizacion", target = "fechaActualizacion")
    @Mapping(source = "usuarioActualizacion", target = "usuarioActualizacion")
    Medico toDomain(MedicoEntity entity);

    // Domain -> Entity
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombres", target = "nombres")
    @Mapping(source = "apellidos", target = "apellidos")
    @Mapping(source = "cmp", target = "cmp")
    @Mapping(source = "especialidad", target = "especialidad") // usa EspecialidadMapper
    @Mapping(source = "fechaCreacion", target = "fechaCreacion")
    @Mapping(source = "usuarioCreacion", target = "usuarioCreacion")
    @Mapping(source = "fechaActualizacion", target = "fechaActualizacion")
    @Mapping(source = "usuarioActualizacion", target = "usuarioActualizacion")
    MedicoEntity toEntity(Medico model);
}
