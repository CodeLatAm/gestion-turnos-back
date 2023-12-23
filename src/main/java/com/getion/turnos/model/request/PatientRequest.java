package com.getion.turnos.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "El apellido es requerido")
    private String surname;
    @NotBlank(message = "La obra social es requerida")
    private String healthInsurance; //obra social
    @NotBlank(message = "El genero es requerido")
    private String Genre;
    private String plan;
    private int affiliateNumber;
    private LocalDate dateOfBirth;
    @NotBlank(message = "El codigo de area es requerido")
    private int areaCode;
    @NotBlank(message = "El celular es requerido")
    private int cellphone;
    private String landline;
    private String nationality;
    private String province;
    @NotBlank(message = "La direccion es requerida")
    private String address;
    private String profession;
    @NotBlank(message = "El email es requerido")
    @Email(message = "Ingresa un email valido")
    private String email;

}
