package com.getion.turnos.service;

import com.getion.turnos.exception.PatientAlreadyExistExceptions;
import com.getion.turnos.mapper.PatientMapper;
import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.PatientRequest;
import com.getion.turnos.model.response.PatientResponse;
import com.getion.turnos.repository.PatientRepository;
import com.getion.turnos.service.injectionDependency.PatientService;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final UserService userService;

    @Transactional
    @Override
    public void createPatient(Long userId, PatientRequest request) {
        log.info("Entrado al servicio createPatient()");

        UserEntity user = userService.findById(userId);
        if(patientRepository.existsByDni(request.getDni())){
            throw new PatientAlreadyExistExceptions("El Paciente ya se encuentra registrado");
        }
        Patient patient = patientMapper.mapToPatientRequest(request);
        user.addPatient(patient);
        patientRepository.save(patient);

    }

    @Override
    public List<PatientResponse> searchPatient(String term) {
        if(term.equals("")){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La busqueda no puede estar vacia");
        }
        List<Patient> patients = patientRepository.searchPatient(term);
        List<PatientResponse> responses = patientMapper.mapToListPatients(patients);
        return responses;
    }

    @Override
    public Patient findByDni(String dni) {
        return patientRepository.findByDni(dni);
    }

}
