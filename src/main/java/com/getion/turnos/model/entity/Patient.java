package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;


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
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;
    /*@ManyToOne
    @JoinColumn(name = "health_center_id")
    private HealthCenterEntity center;
    */

}
