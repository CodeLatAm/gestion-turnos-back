package com.getion.turnos.controller;

import com.getion.turnos.model.request.HealthCenterRequest;
import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.model.response.HealthCenterNamesResponse;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.service.injectionDependency.HealthCenterService;
import com.getion.turnos.service.injectionDependency.ProfileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@Log4j2
@RestController
@RequestMapping("/centers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HealthCenterController {

    private final HealthCenterService healthCenterService;

    @PostMapping("/user/{id}")
    public ResponseEntity<MessageResponse> createCenter(@PathVariable Long id, @Valid @RequestBody HealthCenterRequest request) {
        healthCenterService.createCenter(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED, "Centro medico creado"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<HealthCenterNamesResponse>> getAllCentersName(@PathVariable Long userId) {
        Set<HealthCenterNamesResponse> responses = healthCenterService.getAllCentersName(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/total-patients")
    public ResponseEntity<Integer> totalPatientsByCenter(
            @NotNull(message = "El userId es requerido") @RequestParam Long userId,
            @NotBlank(message = "EL centro es requerido") @RequestParam String centerName) {
        Integer total = healthCenterService.getTotalPatientsByCenter(userId, centerName);
        return ResponseEntity.status(HttpStatus.OK).body(total);
    }

    @GetMapping("/total-centers")
    public ResponseEntity<Integer> totalCentersByUser(
            @NotNull(message = "El userId es requerido") @RequestParam Long userId) {
        Integer total = healthCenterService.getTotalPatientsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(total);
    }
    @DeleteMapping("/delete-patient")
    public ResponseEntity<MessageResponse> deletePatientByCenter(
            @NotNull @RequestParam Long userId,
            @NotBlank(message = "centerName requerido") @RequestParam String centerName,
            @NotNull @RequestParam Long patientId
    )
    {
        healthCenterService.deletePatientByCenter(userId, centerName, patientId);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK, "Paciente eliminado"));
    }

    @DeleteMapping("/delete-center/{id}")
    public ResponseEntity<MessageResponse> deleteCenterById(@PathVariable Long id){
        healthCenterService.deleteCenterById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK, "Centro eliminado"));
    }

}
