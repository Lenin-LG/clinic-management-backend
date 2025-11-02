package com.cibertec.Proyecto.Clinica.Patient.Infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "paciente")
@Getter
@Setter
public class PacienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombres;
    private String apellidos;

    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;

    @Column(length = 8, unique = true)
    private String dni;

    private String direccion;
    private String telefono;

    @Column(unique = true)
    private String email;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "usuario_creacion")
    private String usuarioCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDate fechaActualizacion;

    @Column(name = "usuario_actualizacion")
    private String usuarioActualizacion;

}

