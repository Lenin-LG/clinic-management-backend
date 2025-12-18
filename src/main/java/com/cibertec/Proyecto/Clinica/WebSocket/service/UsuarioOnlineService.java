package com.cibertec.Proyecto.Clinica.WebSocket.service;

import com.cibertec.Proyecto.Clinica.WebSocket.model.UsuarioOnline;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UsuarioOnlineService {

    private final Map<String, UsuarioOnline> usuarios = new ConcurrentHashMap<>();

    public void usuarioConectado(String sessionId, UsuarioOnline user) {
        usuarios.put(sessionId, user);
    }

    public void usuarioDesconectado(String sessionId) {
        usuarios.remove(sessionId);
    }

    public Collection<UsuarioOnline> listarOnline() {
        return usuarios.values();
    }
}

