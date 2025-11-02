package com.cibertec.Proyecto.Clinica.Doctor.domain.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
    public class Especialidad {
        private Integer id;
        private String nombre;
        private String descripcion;
    }
