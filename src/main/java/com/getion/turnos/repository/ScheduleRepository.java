package com.getion.turnos.repository;

import com.getion.turnos.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
