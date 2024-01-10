package com.getion.turnos.model.entity;

import com.getion.turnos.enums.DayOfWeekEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    //@Enumerated(EnumType.STRING)
    //private DayOfWeekEnum dayOfWeek;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "patient_id") // Nombre de la columna que actúa como clave foránea en la tabla de turnos
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "schedule_id")  // Nombre de la columna que actúa como clave foránea en la tabla de turnos
    private Schedule schedule;

}
