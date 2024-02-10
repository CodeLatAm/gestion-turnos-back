package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.BusinessHoursRequest;
import com.getion.turnos.model.response.BusinessHoursResponse;

import java.util.List;
import java.util.Set;

public interface BusinessHoursService {

    void createAttentionDays(BusinessHoursRequest request);
    List<BusinessHoursResponse> getAllBusinessHoursBy(String centerName);

    Set<BusinessHoursResponse> getAllBusinessHoursByCenterAndUserIdAndDay(String centerName, Long userId, String day);

    Set<BusinessHoursResponse> getAllBusinessHoursByCenterAndUserId(String centerName, Long userId);
}
