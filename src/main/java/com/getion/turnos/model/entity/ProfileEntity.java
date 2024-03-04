package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity image;



}
