package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.in.rest.controller;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.http.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("mysql-test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EspecialidadControllerTest {
    @Container
    @ServiceConnection
    protected static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("clinica_test")
            .withUsername("root")
            .withPassword("root");

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String accessToken;
    protected HttpHeaders headers;

    @BeforeEach
    void setup() {
        // Login to obtain token
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
    void testListarEspecialidadesPaginado() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/especialidades?page=0&size=5",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).contains("content");
    }

    @Test
    void testObtenerEspecialidadPorId() {
        Long especialidadId = 1L; // Test ID loaded with Flyway or your initial SQL

        ResponseEntity<Especialidad> response = restTemplate.exchange(
                "/api/especialidades/" + especialidadId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Especialidad.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Especialidad especialidad = response.getBody();
        Assertions.assertThat(especialidad).isNotNull();
        Assertions.assertThat(especialidad.getNombre()).isEqualTo("Cardiología"); // Adjust based on initial data
    }

    @Test
    void testCrearEspecialidad() {
        Especialidad nueva = new Especialidad();
        nueva.setNombre("Neurología 2");
        nueva.setDescripcion("Especialidad del sistema nervioso");

        HttpEntity<Especialidad> request = new HttpEntity<>(nueva, headers);

        ResponseEntity<Especialidad> response = restTemplate.postForEntity(
                "/api/especialidades",
                request,
                Especialidad.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Especialidad creada = response.getBody();
        Assertions.assertThat(creada).isNotNull();
        Assertions.assertThat(creada.getId()).isNotNull();
        Assertions.assertThat(creada.getNombre()).isEqualTo("Neurología 2");
    }
    @Test
    void testAgregarEspecialidad_DuplicadaDevuelveBadRequest() {
        // Create the first specialty
        Especialidad e1 = new Especialidad();
        e1.setNombre("Cardiología Test");
        e1.setDescripcion("Área del corazón");

        ResponseEntity<Especialidad> response1 = restTemplate.postForEntity(
                "/api/especialidades",
                new HttpEntity<>(e1, headers),
                Especialidad.class
        );

        Assertions.assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Try creating another one with the same name
        Especialidad e2 = new Especialidad();
        e2.setNombre("Cardiología Test"); // <-- mismo nombre
        e2.setDescripcion("Duplicada");

        ResponseEntity<String> response2 = restTemplate.postForEntity(
                "/api/especialidades",
                new HttpEntity<>(e2, headers),
                String.class
        );

        //  Verifications
        Assertions.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(response2.getBody()).contains("Duplicate");
    }

    @Test
    void testActualizarEspecialidad() {
        Long especialidadId = 1L; // ID existente

        ResponseEntity<Especialidad> getResponse = restTemplate.exchange(
                "/api/especialidades/" + especialidadId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Especialidad.class
        );

        Especialidad existente = getResponse.getBody();
        Assertions.assertThat(existente).isNotNull();

        existente.setNombre("Cardiología Avanzada");

        HttpEntity<Especialidad> updateRequest = new HttpEntity<>(existente, headers);

        ResponseEntity<Especialidad> updateResponse = restTemplate.exchange(
                "/api/especialidades/" + especialidadId,
                HttpMethod.PUT,
                updateRequest,
                Especialidad.class
        );

        Assertions.assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(updateResponse.getBody().getNombre()).isEqualTo("Cardiología Avanzada");
    }
    @Test
    void testActualizarEspecialidad_InexistenteDevuelveBadRequest() {
        Long idInexistente = 9999L;// ID that does not exist in the database

        Especialidad especialidad = new Especialidad();
        especialidad.setNombre("No importa");
        especialidad.setDescripcion("Prueba error");

        HttpEntity<Especialidad> request = new HttpEntity<>(especialidad, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/especialidades/" + idInexistente,
                HttpMethod.PUT,
                request,
                String.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(response.getBody()).contains("Especialidad con ID 9999 no encontrada");
    }

    @Test
    void testEliminarEspecialidad() {
        Long especialidadId = 7L; // Test ID

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/especialidades/" + especialidadId,
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class
        );
        Assertions.assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}