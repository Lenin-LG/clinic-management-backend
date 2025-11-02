package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  Configuration properties for JWT.
 * Loaded from the security.jwt prefix in application.yml
 */
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    /**
     *Secret key for signing tokens. It can be in Base64 if secretBase64=true
     */
    private String secret;
    /**
     * Access token expiration in milliseconds
     */
    private long accessTokenExpiration;
    /**
     *Refresh token expiration in milliseconds
     */
    private long refreshTokenExpiration;
}
