package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.out.persistence.repository;

import com.cibertec.Proyecto.Clinica.Authentication.application.ports.out.JwtConfigPort;
import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtConfigAdapter  implements JwtConfigPort {

    private final JwtProperties jwtProperties;

    @Override
    public String getSecret() {
        return jwtProperties.getSecret();
    }

    @Override
    public long getAccessTokenExpiration() {
        return jwtProperties.getAccessTokenExpiration();
    }

    @Override
    public long getRefreshTokenExpiration() {
        return jwtProperties.getRefreshTokenExpiration();
    }
}
