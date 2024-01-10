package com.getion.turnos.service;

import com.getion.turnos.exception.BusinessHourAlreadyInUseException;
import com.getion.turnos.mapper.BusinessHoursMapper;
import com.getion.turnos.model.entity.BusinessHours;
import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;
import com.getion.turnos.model.request.BusinessHoursRequest;
import com.getion.turnos.model.response.BusinessHoursResponse;
import com.getion.turnos.repository.BusinessHoursRepository;
import com.getion.turnos.service.injectionDependency.BusinessHoursService;
import com.getion.turnos.service.injectionDependency.HealthCenterService;
import com.getion.turnos.service.injectionDependency.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Log4j2
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
        log.info("schedule: {}, StartTime: {}, EndTime: {}, ScheduleId: {}", schedule, request.getStartTime(), request.getEndTime());

        if(businessHoursRepository.existsByScheduleAndStartTimeAndEndTimeAndDay(
                schedule,
                request.getStartTime(),
                request.getEndTime(),
                request.getDay()
        )) {

        log.error("El horario ya está en uso para el día y hora especificadas.");
            throw new BusinessHourAlreadyInUseException("El horario ya está en uso para el día y hora especificadas.");
        }
        BusinessHours hours = businessHoursMapper.mapToBusinessHourRequest(request);
        hours.setSchedule(schedule);
        schedule.addBusinessHours(hours);

        businessHoursRepository.save(hours);
    }

    @Override
    public List<BusinessHoursResponse> getAllBusinessHoursBy(String centerName) {
        HealthCenterEntity center = healthCenterService.finByName(centerName);
        List<BusinessHoursResponse> responses = businessHoursMapper.mapToBusinessHourEntityList(center.getSchedule().getBusinessHours());
        return responses;
    }
}
