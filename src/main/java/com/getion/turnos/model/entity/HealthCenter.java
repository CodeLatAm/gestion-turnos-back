package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "health_centers")
public class HealthCenter {

    private Long id;
    private String name;
    private String address;
    @OneToOne(mappedBy = "healthCenter", cascade = CascadeType.ALL)
    private Schedule schedule;
    @ManyToMany
    @JoinTable(
            name = "health_center_patient",
            joinColumns = @JoinColumn(name = "health_center_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private Set<Patient> patientSet = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public void addPatient(Patient patient){
        patientSet.add(patient);
    }


}
