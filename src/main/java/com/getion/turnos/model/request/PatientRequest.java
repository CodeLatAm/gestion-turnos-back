package com.getion.turnos.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRequest {

    private String name;
    private String surname;
    private String healthInsurance; //obra social
    private String Genre;
    private int affiliateNumber;
    private LocalDate dateOfBirth;
    private String cellphone;
    private String landline;
    private String nationality;
    private String province;
    private String address;
    private String profession;
}
