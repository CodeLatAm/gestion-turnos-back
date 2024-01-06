package com.getion.turnos.service;

import com.getion.turnos.mapper.BusinessHoursMapper;
import com.getion.turnos.model.entity.BusinessHours;
import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;
import com.getion.turnos.model.request.BusinessHoursRequest;
import com.getion.turnos.repository.BusinessHoursRepository;
import com.getion.turnos.service.injectionDependency.BusinessHoursService;
import com.getion.turnos.service.injectionDependency.HealthCenterService;
import com.getion.turnos.service.injectionDependency.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BusinessHoursServiceImpl implements BusinessHoursService {

    private final BusinessHoursRepository businessHoursRepository;
    private final ScheduleService scheduleService;
    private final HealthCenterService healthCenterService;
    private final BusinessHoursMapper businessHoursMapper;

    @Transactional
    @Override
    public void createAttentionDays(BusinessHoursRequest request) {
        HealthCenterEntity center = healthCenterService.finByName(request.getCenterName());
        Schedule schedule = center.getSchedule();
        BusinessHours hours = businessHoursMapper.mapToBusinessHourRequest(request);
        hours.setSchedule(schedule);
        schedule.addBusinessHours(hours);
        businessHoursRepository.save(hours);

    }
}
