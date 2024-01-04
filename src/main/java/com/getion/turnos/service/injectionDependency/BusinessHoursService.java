package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.BusinessHoursRequest;

public interface BusinessHoursService {
    void createAttentionDays(BusinessHoursRequest request);
}
