package com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.out.persistence.mapper;

import com.cibertec.Proyecto.Clinica.Appointment.domain.Model.CitaMedica;
import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.out.persistence.entity.CitaMedicaEntity;
import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.mapper.MedicoMapper;
import com.cibertec.Proyecto.Clinica.Patient.Infrastructure.adapters.out.persistence.mapper.PacienteMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {PacienteMapper.class, MedicoMapper.class})
public interface CitaMedicaMapper {

    CitaMedicaMapper INSTANCE = Mappers.getMapper(CitaMedicaMapper.class);

    @Mapping(target = "paciente", source = "paciente")
    @Mapping(target = "medico", source = "medico")
    CitaMedica toDomain(CitaMedicaEntity entity);

    @Mapping(target = "paciente", source = "paciente")
    @Mapping(target = "medico", source = "medico")
    CitaMedicaEntity toEntity(CitaMedica model);
}
