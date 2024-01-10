package com.getion.turnos.model.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String day;
    @NotNull(message = "La hora de inicio es requerida")
    private LocalTime startTime;
    @NotNull(message = "La hora de cierre es requerida")
    private LocalTime endTime;

}
