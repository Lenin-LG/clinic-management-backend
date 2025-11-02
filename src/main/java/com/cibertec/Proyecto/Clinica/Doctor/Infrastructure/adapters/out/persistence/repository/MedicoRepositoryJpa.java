package com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.repository;
import com.cibertec.Proyecto.Clinica.Doctor.Infrastructure.adapters.out.persistence.entity.MedicoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepositoryJpa extends JpaRepository<MedicoEntity, Integer> {

    @Query("SELECT m FROM MedicoEntity m JOIN FETCH m.especialidad e")
    Page<MedicoEntity> findAllMedicosConEspecialidad(Pageable pageable);
}
