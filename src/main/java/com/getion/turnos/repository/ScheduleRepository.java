package com.getion.turnos.repository;

import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<HealthCenterEntity> findByName(String centerName);
}
