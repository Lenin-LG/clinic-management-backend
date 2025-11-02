package com.cibertec.Proyecto.Clinica.Doctor.domain.Model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medico {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String cmp; // código de colegiatura único
    private Especialidad especialidad; // Relación con dominio Especialidad
    private LocalDateTime fechaCreacion;
    private String usuarioCreacion;
    private LocalDateTime fechaActualizacion;
    private String usuarioActualizacion;
}
