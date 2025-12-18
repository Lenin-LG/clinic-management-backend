package com.cibertec.Proyecto.Clinica.Authentication.application.services;

import com.cibertec.Proyecto.Clinica.Authentication.application.ports.out.UsuarioPersistencePort;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.UsuarioModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObtenerUsuariosUseCase {

    private final UsuarioPersistencePort usuarioPersistencePort;

    public Page<UsuarioModel> ejecutar(Pageable pageable) {
        return usuarioPersistencePort.obtenerUsuarios(pageable);
    }
}
