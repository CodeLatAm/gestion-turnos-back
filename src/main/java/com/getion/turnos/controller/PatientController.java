package com.getion.turnos.controller;


import com.getion.turnos.model.request.PatientRequest;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.service.injectionDependency.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/{userId}")
    public ResponseEntity<MessageResponse> createPatient(@PathVariable Long userId, @Valid @RequestBody PatientRequest request){
        log.info("Entrando al controller createPatient");
        log.info("UserId: "+ userId + " Request:" + request.toString());
        MessageResponse response = new MessageResponse(HttpStatus.CREATED, "Alta de paciente con exito.");
        patientService.createPatient(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
