package com.cibertec.Proyecto.Clinica.WebSocket.controller;

import com.cibertec.Proyecto.Clinica.WebSocket.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWsController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/private")
    public void chatPrivado(ChatMessage msg, Principal principal) {
        if (principal == null) {
            throw new IllegalStateException("Usuario no autenticado en WebSocket");
        }
        System.out.println("Mensaje recibido de: " + principal.getName() + " para: " + msg.getTo());
        System.out.println("Contenido: " + msg.getContent());
        String from = principal.getName();

        ChatMessage response = new ChatMessage(from, msg.getTo(), msg.getContent());

        // enviar al receptor
        messagingTemplate.convertAndSendToUser(
                msg.getTo(),
                "/queue/messages",
                response
        );

        // enviar copia al emisor
        messagingTemplate.convertAndSendToUser(
                from,
                "/queue/messages",
                response
        );
    }

}
