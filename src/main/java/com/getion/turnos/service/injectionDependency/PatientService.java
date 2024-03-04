package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.ClinicHistoryRequest;
import com.getion.turnos.model.request.PatientRequest;
import com.getion.turnos.model.response.GetTotalGendersResponse;
import com.getion.turnos.model.response.PatientPageResponse;
import com.getion.turnos.model.response.PatientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface PatientService {
    void createPatient(Long userId, PatientRequest request);

    List<PatientResponse> searchPatient(String term, Long userId);

    Patient findByDni(String dni);

    Page<PatientPageResponse> getPatientPage(Long userId, String centerName, PageRequest of);

    Integer getTotalPatientsByCenterNameAndUser(Long userId);

    PatientPageResponse getPatientByIdAndUserId(Long patientId, Long userId);

    void updatePatient(Long patientId, Long userId, PatientRequest request);


    Patient findByIdAndUser(Long patientId, UserEntity user);

    Patient findById(Long patientId);

    PatientPageResponse findByIdPatientResponse(Long id);


    List<PatientPageResponse> getAllPatientsByCenterNameAndUserId(String centerName, Long userId);

    List<PatientPageResponse> searchPatientsByTerm(String centerName, Long userId, String term);

    List<PatientPageResponse> filtersPatients(Long userId, String term);


    GetTotalGendersResponse getTotalGenders(Long userId);

    Page<PatientPageResponse> getPatientPagByTerme(Long userId, String centerName, String term, PageRequest of);


    void deletePatient(Patient patient);
}
