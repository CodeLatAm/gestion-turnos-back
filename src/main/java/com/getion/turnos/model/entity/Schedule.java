package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
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
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Turn> turnList = new ArrayList<>();
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusinessHours> businessHours = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "health_center_id")
    private HealthCenterEntity healthCenter;
    // Método auxiliar para agregar BusinessHours
    public void addBusinessHours(BusinessHours businessHours) {
        this.businessHours.add(businessHours);

    }
    // Método auxiliar para quitar BusinessHours
    public void removeBusinessHours(BusinessHours businessHours) {
        this.businessHours.remove(businessHours);


    }
    public void removeTurn(Turn turn){
        turnList.remove(turn);
        turn.setSchedule(this);
    }
    public void addTurn(Turn turn){
        turnList.add(turn);
        turn.setSchedule(this);
    }

}
