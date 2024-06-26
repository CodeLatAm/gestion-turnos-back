package com.getion.turnos.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicHistoryRequest {

    @NotBlank(message = "Es requerido")
    private String centerName;
    @NotBlank(message = "La fecha es requerida")
    private LocalDate localDate;
    @NotBlank(message = "El motivo es requerido")
    private String reasonForConsultation;
    private String background; // Antecedentes
    private String physicalExam; // ExamenFísico;
    private String complementaryStudies; // EstudiosComplementarios;
    private String observations; // Observaciones;
}
