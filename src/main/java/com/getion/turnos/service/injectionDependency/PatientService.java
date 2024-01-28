package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.request.PatientRequest;
import com.getion.turnos.model.response.PatientResponse;

import java.util.List;

public interface PatientService {
    void createPatient(Long userId, PatientRequest request);

    List<PatientResponse> searchPatient(String term);

    Patient findByDni(String dni);
}
