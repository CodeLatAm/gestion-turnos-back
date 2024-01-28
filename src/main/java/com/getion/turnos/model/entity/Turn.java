package com.getion.turnos.model.entity;

import com.getion.turnos.enums.ShiftStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "turns")
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String centerName;
    private LocalDate date;
    private String hour;
    private String patientDni;
    private boolean availability;
    private ShiftStatus shiftStatus;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

}
