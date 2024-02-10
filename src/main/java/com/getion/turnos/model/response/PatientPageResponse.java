package com.getion.turnos.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientPageResponse {

    private Long id;
    private String name;
    private String surname;
    private String healthInsurance; //obra social
    private String genre;
    private String affiliateNumber;
    private LocalDate dateOfBirth;
    private String cellphone;
    private String landline;
    private String nationality;
    private String province;
    private String address;
    private String profession;
    private String email;
    private String dni;
    private String plan;
    private String age;
}
