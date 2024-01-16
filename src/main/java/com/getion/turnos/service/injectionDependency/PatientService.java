package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.PatientRequest;

public interface PatientService {
    void createPatient(Long userId, PatientRequest request);
}
