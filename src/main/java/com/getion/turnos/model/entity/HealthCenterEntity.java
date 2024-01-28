package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "health_centers")
public class HealthCenterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String specialty;
    @OneToOne(mappedBy = "healthCenter", cascade = CascadeType.ALL)
    private Schedule schedule = new Schedule();
    @OneToMany()
    private List<Patient> patientSet = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public void addPatient(Patient patient){
        if(!patientSet.contains(patient)) {
            patientSet.add(patient);
        }
    }


}
