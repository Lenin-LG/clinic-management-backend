package com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.out.persistence.entity;

import com.cibertec.Proyecto.Clinica.Appointment.domain.enums.EstadoCita;
import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.MedicoEntity;
import com.cibertec.Proyecto.Clinica.Patient.Infrastructure.adapters.out.persistence.entity.PacienteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "citamedica")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaMedicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate fecha;

    private LocalTime hora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPaciente", referencedColumnName = "id", nullable = false)
    private PacienteEntity paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMedico", referencedColumnName = "id", nullable = false)
    private MedicoEntity medico;

    private String motivo;

    @Enumerated(EnumType.STRING) // Mapea el ENUM como texto en la BD
    private EstadoCita estado;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "usuario_creacion", updatable = false)
    private String usuarioCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "usuario_actualizacion")
    private String usuarioActualizacion;
}
