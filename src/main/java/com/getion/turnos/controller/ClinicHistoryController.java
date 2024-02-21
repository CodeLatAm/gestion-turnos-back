package com.getion.turnos.controller;

import com.getion.turnos.model.request.ClinicHistoryRequest;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.service.injectionDependency.ClinicHistoryService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clinic-history")
@RequiredArgsConstructor
public class ClinicHistoryController {

    private final ClinicHistoryService clinicHistoryService;

    @PostMapping("/add-patient")
    public ResponseEntity<MessageResponse> addClinicHistory(
            @NotNull(message = "El patientId es requerido") @RequestParam Long patientId,
            @NotNull(message = "El userId es requerido") @RequestParam Long userId,
            @RequestBody ClinicHistoryRequest request
    ){
        clinicHistoryService.addClinicHistory(patientId,userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED, "Historia creada"));
    }

}
