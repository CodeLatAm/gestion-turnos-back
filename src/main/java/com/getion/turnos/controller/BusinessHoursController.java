package com.getion.turnos.controller;

import com.getion.turnos.model.request.BusinessHoursRequest;
import com.getion.turnos.model.response.BusinessHoursResponse;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.service.injectionDependency.BusinessHoursService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/days")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BusinessHoursController {

    private final BusinessHoursService businessHoursService;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createAttentionDays(@Valid @RequestBody BusinessHoursRequest request){
        businessHoursService.createAttentionDays(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED, "Días de atencion creados"));
    }
    @GetMapping("/centerName")
    public ResponseEntity<List<BusinessHoursResponse>> getAllBusinessHours(@Valid @RequestParam String centerName){
        List<BusinessHoursResponse> responses = businessHoursService.getAllBusinessHoursBy(centerName);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @GetMapping("/business-hours-day")
    public ResponseEntity<Set<BusinessHoursResponse>> getAllBusinessHoursByCenterAndUserIdAndDay(
            @RequestParam @NotBlank(message = "El centro es requerido") String centerName,
            @RequestParam @NotNull(message = "El id del user es requerido") Long userId,
            @RequestParam @NotBlank(message = "El día es requerido") String day) {
        Set<BusinessHoursResponse> responses = businessHoursService.getAllBusinessHoursByCenterAndUserIdAndDay(centerName, userId, day);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/business-hours")
    public ResponseEntity<Set<BusinessHoursResponse>> getAllBusinessHoursByCenterAndUserId(
            @RequestParam @NotBlank(message = "El centro es requerido") String centerName,
            @RequestParam @NotNull(message = "El id del user es requerido") Long userId) {
        Set<BusinessHoursResponse> responses = businessHoursService.getAllBusinessHoursByCenterAndUserId(centerName, userId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

}
