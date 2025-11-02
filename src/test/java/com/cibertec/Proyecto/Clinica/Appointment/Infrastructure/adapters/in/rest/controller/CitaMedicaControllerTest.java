package com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.in.rest.controller;

import com.cibertec.Proyecto.Clinica.Appointment.domain.Model.CitaMedica;
import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Medico;
import com.cibertec.Proyecto.Clinica.Patient.domain.Model.Paciente;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("mysql-test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CitaMedicaControllerTest {
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
        // Initial login to obtain token
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
    void testCrearYObtenerCita() {
        // --- Create patient ---
        Paciente paciente = new Paciente();
        paciente.setId(1); // It must already exist or be mocked.
        paciente.setNombres("María");
        paciente.setApellidos("Gonzales Pérez");

        // --- Create doctor ---
        Medico medico = new Medico();
        medico.setId(1);
        medico.setNombres("José");
        medico.setApellidos("Martínez López");

        // --- create appointment ---
        CitaMedica cita = new CitaMedica();
        cita.setMotivo("Chequeo general");
        cita.setFecha(LocalDate.of(2025, 11, 5));
        cita.setHora(LocalTime.of(10, 0));
        cita.setPaciente(paciente);
        cita.setMedico(medico);

        HttpEntity<CitaMedica> request = new HttpEntity<>(cita, headers);

        ResponseEntity<CitaMedica> response = restTemplate.postForEntity(
                "/api/citas",
                request,
                CitaMedica.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        CitaMedica creada = response.getBody();
        Assertions.assertThat(creada).isNotNull();
        Assertions.assertThat(creada.getId()).isNotNull();
        Assertions.assertThat(creada.getMotivo()).isEqualTo("Chequeo general");

        // --- get by  ID ---
        ResponseEntity<CitaMedica> getResponse = restTemplate.exchange(
                "/api/citas/" + creada.getId(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                CitaMedica.class
        );

        Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getResponse.getBody()).isNotNull();
        Assertions.assertThat(getResponse.getBody().getMotivo()).isEqualTo("Chequeo general");
    }

    @Test
    void testListarCitas() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/citas?page=0&size=5",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).contains("content");
    }

    @Test
    void testActualizarCita() {
        ResponseEntity<CitaMedica> getResponse = restTemplate.exchange(
                "/api/citas/1",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                CitaMedica.class
        );

        Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        CitaMedica existente = getResponse.getBody();
        Assertions.assertThat(existente).isNotNull();

        // --- Update ---
        existente.setMotivo("Dolor en el pecho - Actualizado");

        HttpEntity<CitaMedica> updateRequest = new HttpEntity<>(existente, headers);

        // --- Send  ---
        ResponseEntity<CitaMedica> updateResponse = restTemplate.exchange(
                "/api/citas/" + existente.getId(),
                HttpMethod.PUT,
                updateRequest,
                CitaMedica.class
        );

        Assertions.assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(updateResponse.getBody()).isNotNull();
        Assertions.assertThat(updateResponse.getBody().getMotivo())
                .isEqualTo("Dolor en el pecho - Actualizado");

        // --- Verify persistence ---
        ResponseEntity<CitaMedica> verifyResponse = restTemplate.exchange(
                "/api/citas/1",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                CitaMedica.class
        );

        Assertions.assertThat(verifyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(verifyResponse.getBody().getMotivo())
                .isEqualTo("Dolor en el pecho - Actualizado");
    }

    @Test
    void testEliminarCita() {
        Long idCita = 2L; // ID of an appointment that you know exists in the test database

        // Obtain the appointment to confirm it exists before deleting
        ResponseEntity<CitaMedica> getResponse = restTemplate.exchange(
                "/api/citas/" + idCita,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                CitaMedica.class
        );

        Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getResponse.getBody()).isNotNull();

        // Delete quote
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/citas/" + idCita,
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class
        );

        Assertions.assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // Verify that it is no longer possible to obtain
        ResponseEntity<CitaMedica> getAfterDelete = restTemplate.exchange(
                "/api/citas/" + idCita,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                CitaMedica.class
        );

        Assertions.assertThat(getAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}