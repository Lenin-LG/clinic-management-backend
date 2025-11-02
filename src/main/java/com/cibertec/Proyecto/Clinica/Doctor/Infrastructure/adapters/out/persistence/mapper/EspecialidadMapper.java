package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.mapper;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.EspecialidadEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EspecialidadMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "descripcion", source = "descripcion")
    Especialidad toDomain(EspecialidadEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")       // <-- ignoramos para que no intente poner null
    @Mapping(target = "descripcion", source = "descripcion")  // <-- igual aquí
    EspecialidadEntity toEntity(Especialidad model);
}
