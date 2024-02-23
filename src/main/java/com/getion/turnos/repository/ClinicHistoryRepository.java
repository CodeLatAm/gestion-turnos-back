package com.getion.turnos.repository;

import com.getion.turnos.model.entity.ClinicHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicHistoryRepository extends JpaRepository<ClinicHistory, Long> {
    List<ClinicHistory> findByPatientIdAndCenterNameOrderByLocalDateDesc(Long patientId, String centerName);
}
