package com.getion.turnos.service;

import com.getion.turnos.exception.HealthCenterNotFoundException;
import com.getion.turnos.exception.PatientAlreadyExistExceptions;
import com.getion.turnos.exception.PatientNotFoundException;
import com.getion.turnos.mapper.PatientMapper;
import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.ClinicHistoryRequest;
import com.getion.turnos.model.request.PatientRequest;
import com.getion.turnos.model.response.PatientPageResponse;
import com.getion.turnos.model.response.PatientResponse;
import com.getion.turnos.repository.PatientRepository;
import com.getion.turnos.service.injectionDependency.PatientService;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        if (patientRepository.existsByDniAndUser(request.getDni(), user)) {
            throw new PatientAlreadyExistExceptions("El Paciente ya se encuentra registrado para este profesional");
        }
        Patient patient = patientMapper.mapToPatientRequest(request);
        user.addPatient(patient);
        patientRepository.save(patient);

    }

    @Override
    public List<PatientResponse> searchPatient(String term, Long userId) {
        if(term.isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La búsqueda no puede estar vacía");
        }
        List<Patient> patients = patientRepository.searchPatientByTermAndUserId(term, userId);
        List<PatientResponse> responses = patientMapper.mapToListPatients(patients);
        return responses;
    }


    @Override
    public Patient findByDni(String dni) {
        return patientRepository.findByDni(dni);
    }

    @Override
    public Page<PatientPageResponse> getPatientPage(Long userId, String centerName, PageRequest pageRequest) {
        UserEntity user = userService.findById(userId);
        Optional<HealthCenterEntity> centerOptional = user.getCenters().stream()
                .filter(c -> c.getName().equalsIgnoreCase(centerName)).findFirst();
        if (centerOptional.isPresent()) {
            HealthCenterEntity center = centerOptional.get();
            List<Patient> patientList = center.getPatientSet();  // Obtener la lista de pacientes del centro

            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), patientList.size());

            // Verificar que el índice de inicio no sea mayor que el tamaño de la lista
            if (start <= patientList.size()) {
                // Crear una sublista de pacientes dentro del rango válido
                List<Patient> paginatedPatients = patientList.subList(start, end);
                // Convertir la lista paginada a una página de pacientes
                Page<Patient> patientsPage = new PageImpl<>(paginatedPatients, pageRequest, patientList.size());
                // Mapear la página de pacientes a una página de respuestas
                return patientsPage.map(patientMapper::mapToPatientPage);
            } else {
                // Si el índice de inicio es mayor que el tamaño de la lista, devolver una página vacía
                return new PageImpl<>(Collections.emptyList(), pageRequest, 0);
            }
        } else {
            throw new HealthCenterNotFoundException("Centro no encontrado con nombre: " + centerName);
        }
    }

    @Override
    public Integer getTotalPatientsByCenterNameAndUser(Long userId) {
        UserEntity user = userService.findById(userId);
        return user.getPatientSet().size();
    }

    @Override
    public PatientPageResponse getPatientByIdAndUserId(Long patientId, Long userId) {
        UserEntity user = userService.findById(userId);
        Patient patient = patientRepository.findByIdAndUser(patientId, user);
        PatientPageResponse response = patientMapper.mapToPatientPage(patient);
        return response;
    }

    @Transactional
    @Override
    public void updatePatient(Long patientId, Long userId, PatientRequest request) {
        UserEntity user = userService.findById(userId);
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new PatientNotFoundException("Paciente no encontrado con id: " + patientId)
        );
        patientMapper.mapToPatient(request, patient);
        patient.setUser(user);
        patientRepository.save(patient);
    }

    @Override
    public Patient findByIdAndUser(Long patientId, UserEntity user) {
        Patient patient = patientRepository.findByIdAndUser(patientId, user);
        return patient;
    }

    @Override
    public Patient findById(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> {
            throw new PatientNotFoundException("El paciente con id: " + patientId + "no esta registrado" );
        });
        return patient;
    }
    @Override
    public PatientPageResponse findByIdPatientResponse(Long id){
        Patient patient = patientRepository.findById(id).orElseThrow(() -> {
            throw new PatientNotFoundException("El paciente con id: " + id + "no esta registrado" );
        });
        PatientPageResponse response = patientMapper.mapToPatientPage(patient);
        return  response;
    }

    @Override
    public List<PatientPageResponse> getAllPatientsByCenterNameAndUserId(String centerName, Long userId) {
        UserEntity user = userService.findById(userId);
        Optional<HealthCenterEntity> center = user.getCenters().stream().filter(
                center1 -> center1.getName().equalsIgnoreCase(centerName)
        ).findFirst();
        if(center.isPresent()){
            HealthCenterEntity center1 = center.get();
            List<PatientPageResponse> response = patientMapper.mapToPatientPageRsponseList(center1.getPatientSet());
            return response;
        }else {
            throw new HealthCenterNotFoundException("Centro no encontrado: " + centerName);
        }
        //return Collections.emptyList();
        //TODO terminar
    }
}
