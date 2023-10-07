package com.getion.turnos.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private Long id;
    private String name;
    private String lastname;
    private String title;
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
