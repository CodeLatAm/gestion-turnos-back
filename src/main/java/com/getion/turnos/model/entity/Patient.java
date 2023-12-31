package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private String email;
    @ManyToMany(mappedBy = "patientSet")
    private Set<HealthCenterEntity> healthCenterEntities;

}
