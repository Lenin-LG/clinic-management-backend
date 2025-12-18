package com.cibertec.Proyecto.Clinica.WebSocket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UsuarioOnline {
    private String username;
    private String rol;
}
