package com.getion.turnos.model.entity;

import com.getion.turnos.enums.DayOfWeekEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "turns")
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DayOfWeekEnum dayOfWeek;
    private String startDate;
    private String endDate;
    @ManyToOne
    @JoinColumn(name = "patient_id") // Nombre de la columna que actúa como clave foránea en la tabla de turnos
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "schedule_id")  // Nombre de la columna que actúa como clave foránea en la tabla de turnos
    private Schedule schedule;


}
