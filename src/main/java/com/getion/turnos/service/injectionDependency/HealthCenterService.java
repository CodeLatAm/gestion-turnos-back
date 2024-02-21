package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.request.HealthCenterRequest;
import com.getion.turnos.model.response.HealthCenterNamesResponse;


import java.util.Set;

public interface HealthCenterService {

    void createCenter(Long id, HealthCenterRequest request);

    HealthCenterEntity finByName(String centerName);

    Set<HealthCenterNamesResponse> getAllCentersName(Long userId);

    Integer getTotalPatientsByCenter(Long userId, String centerName);

    Integer getTotalPatientsByUser(Long userId);

    void save(HealthCenterEntity healthCenter);
}
