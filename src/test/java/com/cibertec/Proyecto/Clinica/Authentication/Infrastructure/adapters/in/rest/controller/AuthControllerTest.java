package com.cibertec.Proyecto.Clinica.Authentication.Infrastructure.adapters.in.rest.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;
import static org.assertj.core.api.Assertions.*;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("mysql-test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest  {
    @Container
    @ServiceConnection
    protected static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("clinica_test")
            .withUsername("root")
            .withPassword("root");
    protected String accessToken;
    protected HttpHeaders headers;

    @Autowired
    protected TestRestTemplate restTemplate;
    @BeforeEach
    void resetDatabase() {

        // Initial login
        var loginRequest = Map.of(
                "username", "admin",
                "password", "admin123"
        );
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/public/api/auth/login",
                loginRequest,
                Map.class
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        accessToken = (String) response.getBody().get("accessToken");
        Assertions.assertThat(accessToken).isNotBlank();

        headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    @Test
    void loginWithValidCredentialsTest() {
        // --- Given ---
        Map<String, String> loginRequest = Map.of(
                "username", "rrhh",
                "password", "admin123"
        );

        // --- When ---
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/public/api/auth/login",
                loginRequest,
                Map.class
        );

        // --- Then ---
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        Map<String, Object> body = response.getBody();
        assertThat(body).containsKeys("accessToken", "refreshToken", "expiresIn");

        System.out.println("Access Token: " + response.getBody().get("accessToken"));
    }

    @Test
    void loginErrorValidCredentialsTest() {
        // --- Given ---
        Map<String, String> loginRequest = Map.of(
                "username", "rrhh",
                "password", "clave_incorrecta"
        );

        // --- When ---
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/public/api/auth/login",
                loginRequest,
                String.class
        );

        // --- Then ---
        assertThat(response.getStatusCode())
                .isIn(HttpStatus.UNAUTHORIZED, HttpStatus.FORBIDDEN);
    }

    @Test
    void refreshTokenTest() {
        // Paso 1: Login
        Map<String, String> loginRequest = Map.of(
                "username", "rrhh",
                "password", "admin123"
        );

        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(
                "/public/api/auth/login",
                loginRequest,
                Map.class
        );

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> loginBody = loginResponse.getBody();
        assertThat(loginBody).isNotNull();

        String refreshToken = (String) loginBody.get("refreshToken");

        // Paso 2: Refresh token
        Map<String, String> refreshRequest = Map.of("refreshToken", refreshToken);

        ResponseEntity<Map> refreshResponse = restTemplate.postForEntity(
                "/public/api/auth/refresh",
                refreshRequest,
                Map.class
        );

        // --- Then ---
        assertThat(refreshResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> refreshBody = refreshResponse.getBody();

        assertThat(refreshBody).containsKeys("accessToken", "refreshToken", "expiresIn");

        assertThat(refreshBody.get("accessToken")).isNotNull();


    }
}