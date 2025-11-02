package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "medico")
@Getter
@Setter
public class MedicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombres;
    private String apellidos;

    @Column(unique = true, nullable = false, length = 10)
    private String cmp;

    // 🔗 Relación con especialidad (muchos médicos pueden tener una especialidad)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEspecialidad", referencedColumnName = "id", nullable = false)
    private EspecialidadEntity especialidad;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "usuario_creacion")
    private String usuarioCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "usuario_actualizacion")
    private String usuarioActualizacion;
}
