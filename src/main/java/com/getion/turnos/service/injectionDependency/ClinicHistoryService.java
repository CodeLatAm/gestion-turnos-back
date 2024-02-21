package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.ClinicHistoryRequest;

public interface ClinicHistoryService {
    void addClinicHistory(Long patientId, Long userId, ClinicHistoryRequest request);
}
