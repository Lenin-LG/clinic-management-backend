package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.in.rest.controller;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("mysql-test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MedicoControllerTest {
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
        //Initial login to obtain token
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
    void testListarMedicosPaginado() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/medicos?page=0&size=5",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).contains("content"); // Página contiene "content"
    }
    @Test
    void testObtenerMedicoPorId() {
        Long medicoId = 1L; // Using Flyway data

        ResponseEntity<Medico> response = restTemplate.exchange(
                "/api/medicos/" + medicoId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Medico.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Medico medico = response.getBody();
        Assertions.assertThat(medico).isNotNull();
        Assertions.assertThat(medico.getNombres()).isEqualTo("José");
    }
    @Test
    void testCrearMedico() {
        Especialidad especialidad=new Especialidad();
        especialidad.setId(1);
        especialidad.setNombre("Cardiología");
        especialidad.setDescripcion("Especialidad del corazón");
        Medico nuevoMedico = new Medico();
        nuevoMedico.setNombres("Laura");
        nuevoMedico.setApellidos("García Pérez");
        nuevoMedico.setCmp("CMP9999");
        nuevoMedico.setEspecialidad(especialidad);

        HttpEntity<Medico> request = new HttpEntity<>(nuevoMedico, headers);

        ResponseEntity<Medico> response = restTemplate.postForEntity(
                "/api/medicos",
                request,
                Medico.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Medico creado = response.getBody();
        Assertions.assertThat(creado).isNotNull();
        Assertions.assertThat(creado.getId()).isNotNull();
        Assertions.assertThat(creado.getNombres()).isEqualTo("Laura");
    }
    @Test
    void testActualizarMedico() {
        Long medicoId = 2L; // Flyway data
        ResponseEntity<Medico> getResponse = restTemplate.exchange(
                "/api/medicos/" + medicoId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Medico.class
        );

        Medico existente = getResponse.getBody();
        Assertions.assertThat(existente).isNotNull();

        existente.setNombres("Ana - Actualizada");

        HttpEntity<Medico> updateRequest = new HttpEntity<>(existente, headers);

        ResponseEntity<Medico> updateResponse = restTemplate.exchange(
                "/api/medicos/" + medicoId,
                HttpMethod.PUT,
                updateRequest,
                Medico.class
        );

        Assertions.assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(updateResponse.getBody().getNombres()).isEqualTo("Ana - Actualizada");
    }
    @Test
    void testEliminarMedico() {
        Long medicoId = 3L; // Test ID from Flyway

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/medicos/" + medicoId,
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class
        );

        Assertions.assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


}