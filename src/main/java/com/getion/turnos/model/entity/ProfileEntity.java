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
@Table(name = "profile")
public class ProfileEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private String name;
    //private String lastname;
    //private String title;
    //private String email;
    @NotBlank(message = "El domicilio es requerido")
    private String domicile;
    //private String country;
    @NotBlank(message = "El telefono es requerido")
    private String phone;
    @NotBlank(message = "La provincia es requerida")
    private String province;
    @NotBlank(message = "La ciudad es requerida")
    private String city;
    @NotBlank(message = "El whatssap es requerido")
    private String whatsapp;
    @NotBlank(message = "La especialidad es requerida")
    private String specialty;
    @NotBlank(message = "La matricula nacional es requerida")
    private String mat_nac;
    @NotBlank(message = "La matricula de provincia es requerida")
    private String mat_prov;
    @NotBlank(message = "La presentacion es requerida")
    private String presentation;
    //private TiposDeConsultas tipos;
    //private Pago pago;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;






}
