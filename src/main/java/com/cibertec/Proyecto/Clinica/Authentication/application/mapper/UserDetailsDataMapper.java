package com.cibertec.Proyecto.Clinica.Authentication.application.mapper;

import com.cibertec.Proyecto.Clinica.Authentication.application.ports.out.UserDetailsData;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.UsuarioModel;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsDataMapper {
    public static UserDetailsData toUserDetailsData(UsuarioModel usuario) {
        return new UserDetailsData() {
            @Override
            public String getUsername() {
                return usuario.getUsername();
            }

            @Override
            public String NombreCompleto() {
                return usuario.getNombre();
            }

            @Override
            public String getPassword() {
                return usuario.getPassword();
            }

            @Override
            public boolean isActive() {
                return usuario.isActivo();
            }

            @Override
            public Collection<String> getRoles() {
                return usuario.getRoles()
                        .stream()
                        .map(r -> r.getNombre())
                        .collect(Collectors.toList());
            }
        };
    }
}
