package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clinic_history")
public class ClinicHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String centerName;
    private LocalDate localDate;
    private String reasonForConsultation;
    private String background; // Antecedentes
    private String PhysicalExam; // ExamenFísico;
    private String ComplementaryStudies; // EstudiosComplementarios;
    private String Observations; // Observaciones;
    @ManyToOne()
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
