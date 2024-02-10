package com.getion.turnos.model.response;

import com.getion.turnos.enums.ShiftStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TurnResponse {


    private Long id;
    private String centerName;
    private LocalDate date;
    private String hour;
    private String patientDni;
    private boolean availability;
    private ShiftStatus shiftStatus;
    private PatientResponse patientResponse;
}
