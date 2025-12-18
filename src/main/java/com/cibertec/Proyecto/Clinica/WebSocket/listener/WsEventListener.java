package com.cibertec.Proyecto.Clinica.WebSocket.listener;

import com.cibertec.Proyecto.Clinica.WebSocket.service.UsuarioOnlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WsEventListener {

    private final UsuarioOnlineService usuarioService;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void onConnect(SessionConnectedEvent event) {

    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        usuarioService.usuarioDesconectado(sessionId);

        messagingTemplate.convertAndSend(
                "/topic/usuarios",
                usuarioService.listarOnline()
        );
    }
}
