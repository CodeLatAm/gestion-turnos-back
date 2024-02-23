package com.getion.turnos.model.response;

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
public class ClinicHistoryResponse {

    private Long id;
    private String centerName;
    private LocalDate localDate;
    private String reasonForConsultation;
    private String background; // Antecedentes
    private String physicalExam; // ExamenFísico;
    private String complementaryStudies; // EstudiosComplementarios;
    private String observations; // Observaciones;
    private PatientPageResponse patientResponse;
}
