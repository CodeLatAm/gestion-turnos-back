package com.getion.turnos.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TurnResponse {


    private Date startDate;
    private Date endDate;
    private PatientResponse patientResponse;
}
