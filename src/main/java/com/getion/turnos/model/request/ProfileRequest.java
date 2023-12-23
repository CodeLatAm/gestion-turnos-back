package com.getion.turnos.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

    private String name;
    private String lastname;
    private String title;
    private String specialty;
    private String country;
    @NotBlank(message = "El domicilio es requerido")
    private String domicile;
    @NotBlank(message = "El telefono es requerido")
    private String phone;
    @NotBlank(message = "La provincia es requerida")
    private String province;
    @NotBlank(message = "La ciudad es requerida")
    private String city;
    @NotBlank(message = "El whatssap es requerido")
    private String whatsapp;
    @NotBlank(message = "La matricula nacional es requerida")
    private String mat_nac;
    @NotBlank(message = "La matricula de provincia es requerida")
    private String mat_prov;
    @NotBlank(message = "La presentacion es requerida")
    private String presentation;

}
