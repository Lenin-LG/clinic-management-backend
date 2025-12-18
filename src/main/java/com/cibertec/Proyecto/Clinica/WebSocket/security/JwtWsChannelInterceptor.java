package com.cibertec.Proyecto.Clinica.WebSocket.security;

import com.cibertec.Proyecto.Clinica.Authentication.application.ports.in.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtWsChannelInterceptor implements ChannelInterceptor {

    private final TokenService tokenService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) return message;

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            List<String> authHeaders = accessor.getNativeHeader("Authorization");
            if (authHeaders == null || authHeaders.isEmpty()) {
                authHeaders = accessor.getNativeHeader("authorization");
            }

            if (authHeaders != null && !authHeaders.isEmpty()) {

                String authHeader = authHeaders.get(0);

                if (authHeader.startsWith("Bearer ")) {

                    String token = authHeader.substring(7);

                    if (tokenService.esTokenValido(token)) {

                        String username = tokenService.extraerUsuario(token);

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        username,
                                        null,
                                        List.of()
                                );

                        accessor.setUser(authentication);
                    }
                }
            }
        }

        return message;
    }
}
