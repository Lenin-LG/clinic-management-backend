package com.cibertec.Proyecto.Clinica.Authentication.application.services;

import com.cibertec.Proyecto.Clinica.Authentication.application.ports.out.JwtConfigPort;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.RolModel;
import com.cibertec.Proyecto.Clinica.Authentication.domain.Model.UsuarioModel;
import com.cibertec.Proyecto.Clinica.Authentication.application.ports.in.TokenService;
import com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements TokenService {

    private final JwtConfigPort jwtConfigPort;

    /**
     * Genera un Access Token con claims personalizados del usuario.
     */
    @Override
    public String generarTokenAcceso(String username, Collection<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return generarToken(claims, username, jwtConfigPort.getAccessTokenExpiration());
    }

    /**
     * Genera un Refresh Token simple con solo el subject.
     */
    @Override
    public String generarTokenRefresco(String username, Collection<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return generarToken(claims, username, jwtConfigPort.getRefreshTokenExpiration());
    }


    /**
     * Extrae el `sub` (sujeto) del token, que en este caso es el username.
     */
    @Override
    public String extraerUsuario(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    /**
     * Verifica que el token sea válido y esté bien firmado.
     */
    @Override
    public boolean esTokenValido(String token) {
        try {
            Jwts.parser()
                    .verifyWith(obtenerClaveFirma())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // --- Métodos privados ---
    private Map<String, Object> construirClaimsDesdeUsuario(UsuarioModel usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", usuario.getNombre() + " " + usuario.getApellido());
        claims.put("roles", usuario.getRoles()
                .stream()
                .map(RolModel::getNombre)
                .toList());
        return claims;
    }

    private String generarToken(Map<String, Object> claims, String subject, long expiracionMilisegundos) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiracionMilisegundos))
                .signWith(obtenerClaveFirma())
                .compact();
    }

    private SecretKey obtenerClaveFirma() {
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtConfigPort.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extraerTodosLosClaims(String token) {
        return Jwts.parser()
                .verifyWith(obtenerClaveFirma())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extraerTodosLosClaims(token));
    }
}
