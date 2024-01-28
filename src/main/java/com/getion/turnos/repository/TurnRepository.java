package com.getion.turnos.repository;

import com.getion.turnos.model.entity.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {
    List<Turn> findByCenterNameAndDateAndHour(String centerName, LocalDate date, String hour);
}
