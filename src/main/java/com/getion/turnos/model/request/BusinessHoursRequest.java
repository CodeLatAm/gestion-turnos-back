package com.getion.turnos.model.request;

import com.getion.turnos.enums.DayOfWeekEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessHoursRequest {

    @NotBlank(message = "El nombre es requerido")
    private String centerName;
    @NotBlank(message = "El día es requerido")
    @Enumerated(EnumType.STRING)
    private DayOfWeekEnum day;
    @NotBlank(message = "La hora de inicio es requerida")
    private LocalTime startTime;
    @NotBlank(message = "La hora de cierre es requerida")
    private LocalTime endTime;

}
