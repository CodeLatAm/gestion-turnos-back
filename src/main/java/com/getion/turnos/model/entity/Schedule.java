package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Turn> turnList = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "health_center_id")  // Nombre de la columna que actúa como clave foránea en la tabla de agendas
    private HealthCenterEntity healthCenter;

    public void addTurn(Turn turn){
        turnList.add(turn);
    }

}
