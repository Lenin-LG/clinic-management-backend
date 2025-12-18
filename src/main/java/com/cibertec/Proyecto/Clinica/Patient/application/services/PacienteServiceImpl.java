package com.cibertec.Proyecto.Clinica.Patient.application.services;

import com.cibertec.Proyecto.Clinica.Patient.domain.Model.Paciente;
import com.cibertec.Proyecto.Clinica.Patient.application.ports.out.PacientePersistence;
import com.cibertec.Proyecto.Clinica.Patient.application.ports.in.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {
    private final PacientePersistence repository;

    @Override
    public List<Paciente> listar() {
        return repository.findAll();
    }

    @Override
    public Paciente obtener(Integer id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Paciente no Encontrado"));
    }

    @Override
    public Paciente agregar(Paciente paciente) {
        return repository.save(paciente);
    }

    @Override
    public Paciente actualizar(Paciente paciente) {
        return repository.update(paciente);
    }

    @Override
    public void eliminar(Integer id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
        repository.deleteById(id);
    }

    @Override
    public Page<Paciente> listarPaginado(int page, int size) {
        return repository.findAllPaginado(PageRequest.of(page, size));
    }

    @Override
    public Optional<Paciente> buscarPorDni(String dni) {
        return repository.findByDni(dni);
    }

    @Override
    public Paciente registrar(Paciente paciente) {
        return repository.save(paciente);
    }
}
