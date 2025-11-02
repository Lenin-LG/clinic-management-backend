package com.cibertec.Proyecto.Clinica.Authentication.application.ports.in;

import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.SeguridadModel;
public interface SeguridadService {
    SeguridadModel autenticacion(String username, String password);

    SeguridadModel refrescar(String token);
}
