package com.getion.turnos.repository;

import com.getion.turnos.model.entity.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {
    List<Turn> findByCenterNameAndDateAndHour(String centerName, LocalDate date, String hour);

    List<Turn> findAllByCenterName(String centerName);
    @Query("SELECT t FROM Turn t WHERE t.centerName = :centerName AND t.date = :date AND t.hour = :hour")
    Optional<Turn> findByCenterNameAndDateAndHourTurn(@Param("centerName") String centerName,
                                                  @Param("date") LocalDate date,
                                                  @Param("hour") String hour);

    List<Turn> findByUserIdAndDate(Long userId, LocalDate now);
}
