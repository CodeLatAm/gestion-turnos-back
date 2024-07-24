package com.getion.turnos.controller;

import com.getion.turnos.model.request.PatientRequest;
import com.getion.turnos.model.response.GetTotalGendersResponse;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.model.response.PatientPageResponse;
import com.getion.turnos.model.response.PatientResponse;
import com.getion.turnos.service.injectionDependency.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
            @RequestParam int page,
            @RequestParam int size){
        Page<PatientPageResponse> responses = patientService.getPatientPage(userId, centerName, PageRequest.of(page, size));
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/page-term")
    public ResponseEntity<Page<PatientPageResponse>> getPatientsPageByTerm(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String centerName,
            @RequestParam String term,
            @RequestParam int page,
            @RequestParam int size){
        Page<PatientPageResponse> responses = patientService.getPatientPagByTerme(userId, centerName,term, PageRequest.of(page, size));
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/total-patients")
    public ResponseEntity<Integer> getTotalPatientsByCenterNameAndUser(
            @NotNull(message = "El userId es requerido") @RequestParam Long userId){
        Integer total = patientService.getTotalPatientsByCenterNameAndUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(total);
    }
    @GetMapping("/patient-by-id-and-user-id")
    public ResponseEntity<PatientPageResponse> getPatientByIdAndUserId(
            @NotNull(message = "El patientId es requerido") @RequestParam Long patientId,
            @NotNull(message = "EL userId es requerido") @RequestParam Long userId
    ){
        PatientPageResponse response = patientService.getPatientByIdAndUserId(patientId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update-patient")
    public ResponseEntity<MessageResponse> updatePatient(@NotNull @RequestParam Long patientId,
                                                         @NotNull @RequestParam Long userId,
                                                         @Valid @RequestBody PatientRequest request){
        patientService.updatePatient(patientId, userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK, "Paciente actualizado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientPageResponse> getPatientById(@PathVariable Long id){
        PatientPageResponse response = patientService.findByIdPatientResponse(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PatientPageResponse>> searchPatients(
            @NotBlank @RequestParam String centerName,
            @NotNull @RequestParam Long userId,
            @RequestParam(required = false) String term
    ) {
        if (term == null || term.isEmpty()) {
            return getAllPatientsByCenterNameAndUserId(centerName, userId);
        } else {
            return searchPatientsByTerm(centerName, userId, term);
        }
    }

    private ResponseEntity<List<PatientPageResponse>> getAllPatientsByCenterNameAndUserId(String centerName, Long userId) {
        List<PatientPageResponse> responses = patientService.getAllPatientsByCenterNameAndUserId(centerName, userId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    private ResponseEntity<List<PatientPageResponse>> searchPatientsByTerm(String centerName, Long userId, String term) {
        List<PatientPageResponse> responses = patientService.searchPatientsByTerm(centerName, userId, term);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/search-patients")
    public ResponseEntity<List<PatientPageResponse>> searchPatientsByCenterNameAndUser(
            @NotBlank @RequestParam String centerName,
            @NotNull @RequestParam Long userId) {

        return getAllPatientsByCenterNameAndUserId(centerName, userId);
    }

    @GetMapping("/filters")
    public ResponseEntity<List<PatientPageResponse>> filtersPatients(
            @NotNull @RequestParam Long userId,
            @RequestParam(required = false) String term
    ){
        List<PatientPageResponse> responses = patientService.filtersPatients(userId, term);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get-total-genders")
    public ResponseEntity<GetTotalGendersResponse> getTotalGenders(@NotNull @RequestParam Long userId){
        GetTotalGendersResponse responses = patientService.getTotalGenders(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }


}
