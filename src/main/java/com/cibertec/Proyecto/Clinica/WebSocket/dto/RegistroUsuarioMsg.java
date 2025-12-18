package com.cibertec.Proyecto.Clinica.WebSocket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarioMsg {
    private String username;
    private String rol;
}

