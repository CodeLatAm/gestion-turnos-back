package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "profiles")
public class ProfileEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String title;
    private String specialty;
    private String country;
    private String domicile;
    private String phone;
    private String province;
    private String city;
    private String whatsapp;
    private String mat_nac;
    private String mat_prov;
    private String presentation;
    //private TiposDeConsultas tipos;
    //private Pago pago;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;






}
