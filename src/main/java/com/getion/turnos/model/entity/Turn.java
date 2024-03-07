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
    @Enumerated(EnumType.STRING)
    private ShiftStatus shiftStatus;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    private Long userId;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Turn{");
        sb.append("id=").append(id);
        sb.append(", centerName='").append(centerName).append('\'');
        sb.append(", date=").append(date);
        sb.append(", hour='").append(hour).append('\'');
        sb.append(", patientDni='").append(patientDni).append('\'');
        sb.append(", availability=").append(availability);
        sb.append(", shiftStatus=").append(shiftStatus);
        sb.append(", patient=").append(patient);
        sb.append(", schedule=").append(schedule);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
