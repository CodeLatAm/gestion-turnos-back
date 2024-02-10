package com.getion.turnos.controller;

import com.getion.turnos.model.request.PatientRequest;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.model.response.PatientPageResponse;
import com.getion.turnos.model.response.PatientResponse;
import com.getion.turnos.service.injectionDependency.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/term")
    public ResponseEntity<List<PatientResponse>> searchPatient(
            @RequestParam String term,
            @RequestParam Long userId

    ){
        List<PatientResponse> responses = patientService.searchPatient(term, userId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @GetMapping("/page")
    public ResponseEntity<Page<PatientPageResponse>> getPatientsPage(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String centerName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        Page<PatientPageResponse> responses = patientService.getPatientPage(userId, centerName, PageRequest.of(page, size));
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }


}
