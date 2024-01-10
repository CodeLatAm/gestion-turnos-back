package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.BusinessHoursRequest;
import com.getion.turnos.model.response.BusinessHoursResponse;

import java.util.List;

public interface BusinessHoursService {

    void createAttentionDays(BusinessHoursRequest request);
    List<BusinessHoursResponse> getAllBusinessHoursBy(String centerName);
}
