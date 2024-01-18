package com.getion.turnos.repository;

import com.getion.turnos.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

    boolean existsByDni(String dni);

    @Query("SELECT p FROM Patient p WHERE LOWER(p.name) LIKE LOWER(concat('%', :term, '%')) " +
            "OR LOWER(p.surname) LIKE LOWER(concat('%', :term, '%')) " +
            "OR LOWER(p.dni) LIKE LOWER(concat('%', :term, '%'))")
    List<Patient> searchPatient(@Param("term") String term);
}
