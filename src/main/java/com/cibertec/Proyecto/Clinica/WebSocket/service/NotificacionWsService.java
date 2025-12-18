package com.cibertec.Proyecto.Clinica.WebSocket.service;

import com.cibertec.Proyecto.Clinica.WebSocket.dto.NotificacionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificacionWsService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notificarCitaCreada(String mensaje) {
        NotificacionDTO dto = new NotificacionDTO(
                "CITA_CREADA",
                mensaje,
                LocalDateTime.now().toString()
        );

        messagingTemplate.convertAndSend(
                "/topic/notificaciones",
                dto
        );
    }
}
