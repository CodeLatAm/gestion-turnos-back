package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;

public interface ScheduleService {
    HealthCenterEntity finByName(String centerName);
}
