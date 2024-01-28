package com.getion.turnos.controller;

import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.service.injectionDependency.TurnService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/turns")
@RequiredArgsConstructor
@CrossOrigin(origins = ("*"))
@Validated
public class TurnController {

    private final TurnService turnService;

    @PostMapping
    public ResponseEntity<MessageResponse> createPatientTurn(
            @RequestParam @NotBlank(message = "El centro es requerido") String centerName,
            @RequestParam @NotNull LocalDate date,
            @RequestParam @NotBlank(message = "La hora es requerida") String hour,
            @RequestParam @NotBlank(message = "El dni es requerido") String dni
            ){
        turnService.createPatientTurn(centerName, date, hour, dni);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED, "Turno creado con éxito"));
    }
}
