package com.getion.turnos.repository;

import com.getion.turnos.enums.DayOfWeekEnum;
import com.getion.turnos.model.entity.BusinessHours;
import com.getion.turnos.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalTime;

@Repository
public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {

    boolean existsByScheduleAndStartTimeAndEndTimeAndDay(Schedule schedule, LocalTime startTime, LocalTime endTime,String day);
    boolean existsByDayAndStartTimeBeforeAndEndTimeAfterAndSchedule(
            String day,
            LocalTime startTime,
            LocalTime endTime,
            Schedule schedule
    );

}
