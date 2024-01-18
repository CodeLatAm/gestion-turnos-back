package com.getion.turnos.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {

    private String name;
    private String surname;
    private String age;
    private String healthInsurance;
    private String dni;



}
