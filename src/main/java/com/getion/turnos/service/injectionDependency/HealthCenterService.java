package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.request.HealthCenterRequest;
import com.getion.turnos.model.request.ProfileRequest;

public interface HealthCenterService {

    void createCenter(Long id, HealthCenterRequest request);

    HealthCenterEntity finByName(String centerName);
}
