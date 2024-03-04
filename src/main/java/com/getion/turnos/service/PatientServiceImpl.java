package com.getion.turnos.service;

import com.getion.turnos.exception.HealthCenterNotFoundException;
import com.getion.turnos.exception.PatientAlreadyExistExceptions;
import com.getion.turnos.exception.PatientNotFoundException;
import com.getion.turnos.exception.UserNotFoundException;
import com.getion.turnos.mapper.PatientMapper;
import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.entity.Turn;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.ClinicHistoryRequest;
import com.getion.turnos.model.request.PatientRequest;
import com.getion.turnos.model.response.GetTotalGendersResponse;
import com.getion.turnos.model.response.PatientPageResponse;
import com.getion.turnos.model.response.PatientResponse;
import com.getion.turnos.repository.PatientRepository;
import com.getion.turnos.service.injectionDependency.PatientService;
import com.getion.turnos.service.injectionDependency.TurnService;
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

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final UserService userService;
    //private final TurnService turnService;

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
    public Page<PatientPageResponse> getPatientPagByTerme(Long userId, String centerName, String term, PageRequest pageRequest) {
        UserEntity user = userService.findById(userId);
        Optional<HealthCenterEntity> centerOptional = user.getCenters().stream()
                .filter(c -> c.getName().equalsIgnoreCase(centerName)).findFirst();
        if (centerOptional.isPresent()) {
            HealthCenterEntity center = centerOptional.get();
            List<Patient> patientList = center.getPatientSet();  // Obtener la lista de pacientes del centro

            // Convertir el término de búsqueda y los nombres/apellidos de los pacientes a minúsculas
            String searchTermLowerCase = term.toLowerCase();

            // Filtrar la lista de pacientes por el término de búsqueda ignorando mayúsculas y minúsculas
            List<Patient> filteredPatients = patientList.stream()
                    .filter(patient ->
                            patient.getName().toLowerCase().contains(searchTermLowerCase) ||
                                    patient.getSurname().toLowerCase().contains(searchTermLowerCase) ||
                            patient.getDni().contains(searchTermLowerCase) || patient.getAge().contains(term))
                    .collect(Collectors.toList());

            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), filteredPatients.size());

            // Verificar que el índice de inicio no sea mayor que el tamaño de la lista
            if (start <= filteredPatients.size()) {
                // Crear una sublista de pacientes dentro del rango válido
                List<Patient> paginatedPatients = filteredPatients.subList(start, end);
                // Convertir la lista paginada a una página de pacientes
                Page<Patient> patientsPage = new PageImpl<>(paginatedPatients, pageRequest, filteredPatients.size());
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
        log.info("Entrando al servicio paciente con metodo, getAllPatientsByCenterNameAndUserId(S, L)");
        UserEntity user = userService.findById(userId);
        if(centerName.equalsIgnoreCase("Todos")){
            List<Patient> patients = patientRepository.findByUser(user);
            List<PatientPageResponse> responses = patientMapper.mapToPatientPageRsponseList(patients);
            return responses;
        }
            Optional<HealthCenterEntity> center = user.getCenters().stream()
                    .filter(c -> c.getName().equalsIgnoreCase(centerName))
                    .findFirst();
            if (center.isPresent()) {
                HealthCenterEntity center1 = center.get();
                List<PatientPageResponse> response = patientMapper.mapToPatientPageRsponseList(center1.getPatientSet());
                return response;
            } else {
                log.error("Centro no encontrado: " + centerName);
                throw new HealthCenterNotFoundException("Centro no encontrado: " + centerName);
            }
    }

    @Override
    public List<PatientPageResponse> searchPatientsByTerm(String centerName, Long userId, String term) {
        log.info("Entrando al servicio paciente con metodo, searchPatientsByTerm( S, L)");
        UserEntity user = userService.findById(userId);
        Optional<HealthCenterEntity> center = user.getCenters().stream()
                .filter(c -> c.getName().equalsIgnoreCase(centerName))
                .findFirst();
        if (center.isPresent()) {
            HealthCenterEntity center1 = center.get();
            List<Patient> patients = center1.getPatientSet().stream()
                    .filter(p -> containsTerm(p, term))
                    .collect(Collectors.toList());

            return patientMapper.mapToPatientPageRsponseList(patients);
        } else {
            log.error("Centro no encontrado: " + centerName);
            throw new HealthCenterNotFoundException("Centro no encontrado: " + centerName);
        }
    }

    private boolean containsTerm(Patient patient, String term) {
        String termLowerCase = term.toLowerCase();
        // Verificar si el término coincide con las tres primeras letras de cada campo, ignorando mayúsculas y minúsculas
        return patient.getName().toLowerCase().startsWith(termLowerCase) ||
                patient.getSurname().toLowerCase().startsWith(termLowerCase) ||
                patient.getDni().toLowerCase().startsWith(termLowerCase) ||
                patient.getEmail().toLowerCase().startsWith(termLowerCase) ||
                patient.getAge().contains(term);
    }

    @Override
    public List<PatientPageResponse> filtersPatients(Long userId, String term) {
        //UserEntity user = userService.findById(userId);
        this.validateInputs(userId, term);

        List<Patient> patients = patientRepository.searchPatientByTermAndUserId(term, userId);
        return patientMapper.mapToPatientPageRsponseList(patients);
    }

    private void validateInputs(Long userId, String term) {
        if(userId == null){
            throw new UserNotFoundException("El userId es necesario");
        }
        if(term.isEmpty() || term.equals("") || term.length() < 3){
            throw new PatientNotFoundException("La busqueda no puede estar vacia o ser nula o tener menos de tres caracteres");
        }
    }

    @Override
    public GetTotalGendersResponse getTotalGenders(Long userId) {
        Integer totalMale = 0;
        Integer totalFemale = 0;
        Integer totalTransgender = 0;
        UserEntity user = userService.findById(userId);
        Set<Patient> patients = user.getPatientSet();
        for(Patient p : patients){
            if (p.getGenre().equalsIgnoreCase("Masculino"))
                totalMale++;
            if (p.getGenre().equalsIgnoreCase("Femenino"))
                totalFemale++;
            if (p.getGenre().equalsIgnoreCase("Transgénero"))
                totalTransgender++;
        }
        return GetTotalGendersResponse.builder()
                .totalMale(totalMale)
                .totalFemale(totalFemale)
                .totalTransgender(totalTransgender)
                .build();

    }

    @Override
    public void deletePatient(Patient patient) {
        patientRepository.delete(patient);
    }
}
