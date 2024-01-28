package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;
import com.getion.turnos.model.response.ScheduleResponse;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface ScheduleService {
    HealthCenterEntity finByName(String centerName);

    Page<ScheduleResponse> getPaginatedSchedule(int page, int size, String centerName, Date startDate, Date endDate);
}
