package com.getion.turnos.repository;

import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.entity.Turn;
import com.getion.turnos.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {


    List<Turn> findAllByCenterName(String centerName);
    @Query("SELECT t FROM Turn t WHERE t.centerName = :centerName AND t.date = :date AND t.hour = :hour")
    Optional<Turn> findByCenterNameAndDateAndHourTurn(@Param("centerName") String centerName,
                                                  @Param("date") LocalDate date,
                                                  @Param("hour") String hour);

    List<Turn> findByUserIdAndDate(Long userId, LocalDate now);

    List<Turn> findByPatientId(Long patientId);

    void deleteAllByPatientId(Long patientId);

    List<Turn> findByUserIdAndCenterName(Long userId, String centerName);


    List<Turn> findByUserIdAndPatientDni(Long userId, String dni);

    @Query("SELECT t FROM Turn t WHERE t.userId = :userId AND t.patientDni LIKE CONCAT(:dniPrefix, '%')")
    List<Turn> findByUserIdAndPatientDniStartingWith(@Param("userId") Long userId, @Param("dniPrefix") String dniPrefix);
}
