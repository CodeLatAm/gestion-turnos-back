package com.getion.turnos.repository;

import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

    boolean existsByDni(String dni);
    @Query("SELECT p FROM Patient p WHERE (LOWER(p.name) LIKE LOWER(concat('%', :term, '%')) " +
            "OR LOWER(p.surname) LIKE LOWER(concat('%', :term, '%')) " +
            "OR LOWER(p.dni) LIKE LOWER(concat('%', :term, '%'))) " +
            "AND p.user.id = :userId")
    List<Patient> searchPatientByTermAndUserId(@Param("term") String term, @Param("userId") Long userId);

    Patient findByDni(String dni);
    boolean existsByDniAndUser(String dni, UserEntity user);

}
