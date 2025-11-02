package com.cibertec.Proyecto.Clinica.config.Audit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuditorAwareConfigTest {
    private final AuditorAwareConfig auditorAwareConfig = new AuditorAwareConfig();

    @AfterEach
    void limpiarContexto() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void returnUserTest() {
        // --- Given ---
        User user = new User("usuario1", "password", List.of());
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // --- When ---
        Optional<String> result = auditorAwareConfig.getCurrentAuditor();

        // --- Then ---
        assertTrue(result.isPresent());
        assertEquals("usuario1", result.get());
    }

    @Test
    void shouldReturnEmptyWhenThereIsNoAuthenticationTest() {
        // --- Given ---
        SecurityContextHolder.clearContext();

        // --- When ---
        Optional<String> result = auditorAwareConfig.getCurrentAuditor();

        // --- Then ---
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnPrincipalWhenItIsStringTest() {
        // --- Given ---
        Authentication authentication = new UsernamePasswordAuthenticationToken("admin", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // --- When ---
        Optional<String> result = auditorAwareConfig.getCurrentAuditor();

        // --- Then ---
        assertTrue(result.isPresent());
        assertEquals("admin", result.get());
    }
    @Test
    void returnNullWhenIsStringTest() {
        // --- Given ---
        Authentication authentication = new UsernamePasswordAuthenticationToken(12345, null, List.of()); // tipo no esperado
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // --- When ---
        Optional<String> result = auditorAwareConfig.getCurrentAuditor();

        // --- Then ---
        assertTrue(result.isEmpty());
    }


}