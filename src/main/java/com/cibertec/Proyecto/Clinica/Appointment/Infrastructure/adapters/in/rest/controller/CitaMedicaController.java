package com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.in.rest.controller;
import com.cibertec.Proyecto.Clinica.Appointment.application.ports.in.CitaMedicaServicePort;
import com.cibertec.Proyecto.Clinica.Appointment.Infrastructure.adapters.in.rest.dto.CitaMedicaDTO;
import com.cibertec.Proyecto.Clinica.Appointment.domain.Model.CitaMedica;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaMedicaController {

    private final CitaMedicaServicePort citaMedicaServicePort;

    // 🔹 Create new appointment
    @PostMapping
    public ResponseEntity<CitaMedica> crearCita(@RequestBody CitaMedica citaMedica) {
        return ResponseEntity.ok(citaMedicaServicePort.registrarCita(citaMedica));
    }

   // 🔹 Get all appointment
   @GetMapping
   public Page<CitaMedicaDTO> listarCitas(Pageable pageable) {
       return citaMedicaServicePort.listarCitasPaginadas(pageable);
   }

    // 🔹 Search one appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<CitaMedica> obtenerCita(@PathVariable Integer id) {
        return citaMedicaServicePort.obtenerCitaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Update appointment
    @PutMapping("/{id}")
    public ResponseEntity<CitaMedica> actualizarCita(@PathVariable Integer id, @RequestBody CitaMedica citaMedica) {
        citaMedica.setId(id); // Aseguramos que actualice la correcta
        return ResponseEntity.ok(citaMedicaServicePort.actualizarCita(citaMedica));
    }

    // 🔹 Delete apoointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Integer id) {
        citaMedicaServicePort.eliminarCita(id);
        return ResponseEntity.noContent().build();
    }
    // 🔹 Get appointments by doctor and date
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<CitaMedicaDTO>> listarPorMedicoYFecha(
            @PathVariable Integer medicoId,
            @RequestParam LocalDate fecha
    ) {
        return ResponseEntity.ok(
                citaMedicaServicePort.listarCitasPorMedicoYFecha(medicoId, fecha)
        );
    }
}
