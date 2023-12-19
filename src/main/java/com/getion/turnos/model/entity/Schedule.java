package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Turn> turnList;
    @OneToOne
    @JoinColumn(name = "health_center_id")  // Nombre de la columna que actúa como clave foránea en la tabla de agendas
    private HealthCenter healthCenter;

    public void addTurn(Turn turn){
        turnList.add(turn);
    }

}
