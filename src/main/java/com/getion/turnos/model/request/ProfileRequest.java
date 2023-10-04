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


    private String domicile;
    //private String country;

    private String phone;

    private String province;

    private String city;

    private String whatsapp;

    private String specialty;

    private String mat_nac;

    private String mat_prov;

    private String presentation;
}
