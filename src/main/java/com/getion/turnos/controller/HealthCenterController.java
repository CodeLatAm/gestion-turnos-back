package com.getion.turnos.controller;

import com.getion.turnos.model.request.HealthCenterRequest;
import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.service.injectionDependency.HealthCenterService;
import com.getion.turnos.service.injectionDependency.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/centers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HealthCenterController {

    private final HealthCenterService healthCenterService;

    @PostMapping("/user/{id}")
    public ResponseEntity<MessageResponse> createCenter(@PathVariable Long id, @Valid @RequestBody HealthCenterRequest request){
        healthCenterService.createCenter(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED,"Centro medico creado"));
    }
}
