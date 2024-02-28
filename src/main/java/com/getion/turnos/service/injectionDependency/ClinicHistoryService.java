package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.ClinicHistoryRequest;
import com.getion.turnos.model.response.ClinicHistoryResponse;

import java.util.List;

public interface ClinicHistoryService {
    void addClinicHistory(Long patientId, Long userId, ClinicHistoryRequest request);

    List<ClinicHistoryResponse> getAllClinicHistory(Long patientId, String centerName);

    void deletedClinicHistory(Long id);
}
