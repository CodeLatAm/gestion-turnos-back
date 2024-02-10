package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.request.PatientRequest;
import com.getion.turnos.model.response.PatientPageResponse;
import com.getion.turnos.model.response.PatientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PatientService {
    void createPatient(Long userId, PatientRequest request);

    List<PatientResponse> searchPatient(String term, Long userId);

    Patient findByDni(String dni);


    Page<PatientPageResponse> getPatientPage(Long userId, String centerName, PageRequest of);
}
