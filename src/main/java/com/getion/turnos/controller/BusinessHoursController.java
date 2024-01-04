package com.getion.turnos.controller;

import com.getion.turnos.model.request.BusinessHoursRequest;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.service.injectionDependency.BusinessHoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
