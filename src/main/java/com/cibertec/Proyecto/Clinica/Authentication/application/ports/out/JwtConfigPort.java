package com.cibertec.Proyecto.Clinica.Authentication.application.ports.out;

public interface JwtConfigPort {
    String getSecret();
    long getAccessTokenExpiration();
    long getRefreshTokenExpiration();
}
