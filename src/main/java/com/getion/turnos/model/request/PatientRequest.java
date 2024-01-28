package com.getion.turnos.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    private String healthInsurance; //obra social
    private String genre;
    private String plan;
    private String affiliateNumber;
    private LocalDate dateOfBirth;
    @NotBlank(message = "El celular es requerido")
    private String cellphone;
    private String landline;
    private String nationality;
    private String province;
    private String address;
    private String profession;
    @NotBlank(message = "El email es requerido")
    @Email(message = "Ingresa un email valido")
    private String email;
    @NotBlank(message = "El DNI no puede estar en blanco")
    @Pattern(regexp = "^[0-9]{8}$", message = "El formato del DNI es inválido")
    private String dni;
    private String age;
}
