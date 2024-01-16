package com.getion.turnos.repository;

import com.getion.turnos.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByDni(String dni);
}
