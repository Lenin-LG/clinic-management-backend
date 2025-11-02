package com.cibertec.Proyecto.Clinica.Patient.domain.Model;


import lombok.Data;


import java.time.LocalDate;
@Data
public class Paciente {
    private Integer id;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String dni;
    private String direccion;
    private String telefono;
    private String email;
}
