package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.config;

import com.cibertec.Proyecto.Clinica.Authentication.application.mapper.UserDetailsDataMapper;
import com.cibertec.Proyecto.Clinica.Authentication.application.ports.out.UsuarioPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserDetailsServiceAdapter implements UserDetailsService {

    private final UsuarioPersistencePort usuarioPersistencePort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioPersistencePort.usuarioPorUserName(username)
                .map(UserDetailsDataMapper::toUserDetailsData)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario con username: " + username));
    }
}
