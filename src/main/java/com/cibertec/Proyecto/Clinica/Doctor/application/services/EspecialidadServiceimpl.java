package com.cibertec.Proyecto.Clinica.Doctor.application.services;

import com.cibertec.Proyecto.Clinica.Doctor.domain.Model.Especialidad;
import com.cibertec.Proyecto.Clinica.Doctor.application.ports.out.EspecialidadPersistence;
import com.cibertec.Proyecto.Clinica.Doctor.application.ports.in.EspecialidadServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadServiceimpl implements EspecialidadServicePort{

        private final EspecialidadPersistence especialidadPersistence;

        @Override
        public Especialidad guardar(Especialidad especialidad) {
            return especialidadPersistence.save(especialidad);
        }

        @Override
        public Especialidad obtenerPorId(Integer id) {
            return especialidadPersistence.findById(id)
                    .orElseThrow(()-> new RuntimeException("Especialidad no Encontrado"));
        }

        @Override
        public List<Especialidad> listar() {
            return especialidadPersistence.findAll();
        }

        @Override
        public Especialidad actualizar(Integer id, Especialidad especialidad) {
            return especialidadPersistence.actualizar(id, especialidad);
        }


        @Override
        public void eliminar(Integer id) {
            especialidadPersistence.deleteById(id);
        }

        @Override
        public Page<Especialidad> listarPaginado(int page, int size) {
            return especialidadPersistence.findAllPaginado(page, size);
        }

}
