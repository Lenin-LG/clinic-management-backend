package com.cibertec.Proyecto.Clinica.Authentication.application.ports.in;

import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.UsuarioModel;

import java.util.Collection;

public interface TokenService {
    String generarTokenAcceso(String username, Collection<String> roles) ;

    String generarTokenRefresco(String username, Collection<String> roles);

    String extraerUsuario(String token);

    boolean esTokenValido(String token);
}
