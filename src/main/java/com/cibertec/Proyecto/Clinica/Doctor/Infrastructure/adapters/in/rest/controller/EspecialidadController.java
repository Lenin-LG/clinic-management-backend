package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.in.rest.controller;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import com.cibertec.Proyecto.Clinica.Doctor.application.ports.in.EspecialidadServicePort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    private final EspecialidadServicePort especialidadServicePort;

    public EspecialidadController(EspecialidadServicePort service) {
        this.especialidadServicePort = service;
    }

    //  List all
    @GetMapping
    public ResponseEntity<Page<Especialidad>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(especialidadServicePort.listarPaginado(page, size));
    }

    // Get by Id
    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> obtener(@PathVariable Integer id) {
        Especialidad especialidad = especialidadServicePort.obtenerPorId(id);
        return ResponseEntity.ok(especialidad);
    }

    // Register new
    @PostMapping
    public ResponseEntity<?> agregar(@RequestBody Especialidad especialidad) {
        try {
            Especialidad nueva = especialidadServicePort.guardar(especialidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Duplicate");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Especialidad especialidad) {
        try {
            return ResponseEntity.ok(especialidadServicePort.actualizar(id, especialidad));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        especialidadServicePort.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
