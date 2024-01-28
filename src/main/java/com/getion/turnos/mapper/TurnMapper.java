package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.Turn;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TurnMapper {
    public Turn mapToTurnEntity(String centerName, LocalDate date, String hour, String dni) {
        return Turn.builder()
                .centerName(centerName)
                .availability(false)
                .patientDni(dni)
                .hour(hour)
                .date(date)
                .build();
    }
}
