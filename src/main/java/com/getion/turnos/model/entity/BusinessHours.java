package com.getion.turnos.model.entity;

import com.getion.turnos.enums.DayOfWeekEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="business_hours")
public class BusinessHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String centerName;
    private String day;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToOne()
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

}
