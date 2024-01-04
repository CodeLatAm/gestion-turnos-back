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
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusinessHours> businessHours = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "health_center_id")  // Nombre de la columna que actúa como clave foránea en la tabla de agendas
    private HealthCenterEntity healthCenter;

    // Método auxiliar para agregar BusinessHours
    public void addBusinessHours(BusinessHours businessHours) {
        this.businessHours.add(businessHours);

    }
    // Método auxiliar para quitar BusinessHours
    public void removeBusinessHours(BusinessHours businessHours) {
        this.businessHours.remove(businessHours);

    }
    public void addTurn(Turn turn){
        turnList.add(turn);
    }

}
