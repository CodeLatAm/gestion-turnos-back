package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="users")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String title;
    @Basic
    @Column(nullable = false)
    private String username; //email
    private String password;
    private String country;
    private String specialty;
    private LocalDate creationDate;
    private boolean itsVip;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HealthCenterEntity> centers = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ProfileEntity profile;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Patient> patientSet = new HashSet<>();
    @OneToMany()
    private List<Payment> payments = new ArrayList<>();

    public void addCenter(HealthCenterEntity center){
        centers.add(center);
    }

    public void addPatient(Patient patient){
        patientSet.add(patient);
        patient.setUser(this);
    }
}
