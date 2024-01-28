package com.getion.turnos.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnRequest {

    private String centerName;
    private LocalDate date;
    private String hour;
    private String patientDni;
}
