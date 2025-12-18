package com.cibertec.Proyecto.Clinica.WebSocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatMessage {
    private String from;
    private String to;
    private String content;
}
