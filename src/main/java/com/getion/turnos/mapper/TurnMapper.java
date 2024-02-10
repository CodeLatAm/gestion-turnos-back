package com.getion.turnos.mapper;

import com.getion.turnos.enums.ShiftStatus;
import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.entity.Turn;
import com.getion.turnos.model.response.PatientResponse;
import com.getion.turnos.model.response.TurnResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TurnMapper {




    public Turn mapToTurnEntity(String centerName, LocalDate date, String hour, String dni) {
        return Turn.builder()
                .centerName(centerName)
                .availability(false)
                .patientDni(dni)
                .hour(hour)
                .date(date)
                .shiftStatus(ShiftStatus.ASIGNADO)
                .build();
    }

    public List<TurnResponse> mapToTurnEntityList(List<Turn> turns) {
        return turns.stream().map(this::mapToTurnResponse).collect(Collectors.toList());
    }

    public TurnResponse mapToTurnResponse(Turn turn) {
        return TurnResponse.builder()
                .id(turn.getId())
                .centerName(turn.getCenterName())
                .hour(turn.getHour())
                .date(turn.getDate())
                .shiftStatus(turn.getShiftStatus())
                .availability(turn.isAvailability())
                .patientDni(turn.getPatientDni())
                .patientResponse(this.mapToPatient(turn.getPatient()))
                .build();
    }

    private PatientResponse mapToPatient(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .age(patient.getAge())
                .name(patient.getName())
                .healthInsurance(patient.getHealthInsurance())
                .surname(patient.getSurname())
                .dni(patient.getDni())
                .build();
    }

}
