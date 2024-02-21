package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
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
    @ManyToMany
    @JoinTable(
            name = "health_center_patients", // Nombre de la tabla de relación
            joinColumns = @JoinColumn(name = "health_center_id"), // Columna que referencia a este centro de salud
            inverseJoinColumns = @JoinColumn(name = "patient_id") // Columna que referencia al paciente
    )
    private List<Patient> patientSet = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public void addPatient(Patient patient) {
        if (patient != null) {
            boolean patientExists = userEntity.getCenters().stream()
                    .anyMatch(center -> center.getPatientSet().stream()
                            .anyMatch(p -> p.getDni().equals(patient.getDni())));

            if (!patientExists) {
                patientSet.add(patient);

            }
        }
        log.info(patientSet.toString());
    }
    public void addPatient2(Patient patient) {
        if (patient != null) {
            // Verificar si el paciente ya está asociado con este centro de salud
            boolean patientExists = patientSet.stream()
                    .anyMatch(p -> p.getDni().equals(patient.getDni()));

            // Si el paciente no está asociado con este centro, agregarlo
            if (!patientExists) {
                patientSet.add(patient);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HealthCenterEntity{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", specialty='").append(specialty).append('\'');
        sb.append(", patientSet=").append(patientSet);
        sb.append('}');
        return sb.toString();
    }
}
