package com.getion.turnos.service;

import com.getion.turnos.exception.HealthCenterNotFoundException;
import com.getion.turnos.exception.PatientAlreadyExistExceptions;
import com.getion.turnos.mapper.PatientMapper;
import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.entity.UserEntity;
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
            throw new PatientAlreadyExistExceptions("El Paciente ya se encuentra registrado para este usuario");
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
        if(centerOptional.isPresent()){
            HealthCenterEntity center = centerOptional.get();
            List<Patient> patientList = center.getPatientSet();  // Paginar la lista de pacientes
            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), patientList.size());
            List<Patient> paginatedPatients = patientList.subList(start, end);
            // Convertir la lista paginada a una página de pacientes
            Page<Patient> pacientesPage = new PageImpl<>(paginatedPatients, pageRequest, patientList.size());
            return pacientesPage.map(patientMapper::mapToPatientPage);
        }else{
            throw  new HealthCenterNotFoundException("Centro no encontrado con nombre: " + centerName);
        }

    }
}
