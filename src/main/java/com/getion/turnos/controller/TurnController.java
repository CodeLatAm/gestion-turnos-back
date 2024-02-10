package com.getion.turnos.controller;

import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.model.response.TurnResponse;
import com.getion.turnos.service.injectionDependency.TurnService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/turns")
@RequiredArgsConstructor
@CrossOrigin(origins = ("*"))
@Validated
public class TurnController {

    private final TurnService turnService;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createPatientTurn(
            @RequestParam @NotBlank(message = "El centro es requerido") String centerName,
            @RequestParam @NotBlank(message = "La fecha es requerida") String date,
            @RequestParam @NotBlank(message = "La hora es requerida") String hour,
            @RequestParam @NotBlank(message = "El dni es requerido") String dni,
            @RequestParam @NotNull(message = "El userId es requerido") Long userId
            ){
        log.info("Date string = " + date);
        LocalDate localDate = LocalDate.parse(date.split("T")[0]); // Solo toma la parte de la fecha, excluyendo la hora
        log.info("Date localDate = ", localDate);
        turnService.createPatientTurn(centerName, localDate, hour, dni, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED, "Turno creado con éxito"));
    }
    @GetMapping("/turn-by-cdh")
    public ResponseEntity<TurnResponse> getTurnBy(
            @RequestParam @NotBlank(message = "El centro es requerido") String centerName,
            @RequestParam @NotBlank(message = "La fecha es requerida") String date,
            @RequestParam @NotBlank(message = "La hora es requerida") String hour
            ){
        log.info("Date string = " + date);
        LocalDate localDate = LocalDate.parse(date.split("T")[0]);
        log.info("Date localDate = ", localDate);
        TurnResponse response = turnService.getTurnBy(centerName, localDate, hour);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/all")
    public ResponseEntity<List<TurnResponse>> getAllTurns(){
        List<TurnResponse> responses = turnService.getAllTurns();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @GetMapping("/center-name")
    public ResponseEntity<List<TurnResponse>> getAllTurnsByCenterName(
            @RequestParam @NotBlank(message = "El centro es requerido") String centerName){
        List<TurnResponse> responses = turnService.getAllTurnsByCenterName(centerName);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteTurnById(@PathVariable Long id){
        turnService.deleteTurnById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK, "Turno cancelado"));
    }
    @GetMapping("/center-name-userId")
    public ResponseEntity<List<TurnResponse>> getAllTurnsByCenterNameAndUserId(
            @RequestParam @NotBlank(message = "El centro es requerido") String centerName,
            @RequestParam @NotNull(message = "El userId es requerido") Long userId)
    {
        List<TurnResponse> responses = turnService.getAllTurnsByCenterNameAndUserId(centerName,userId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

}
