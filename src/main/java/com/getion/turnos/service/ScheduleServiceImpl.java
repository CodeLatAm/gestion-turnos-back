package com.getion.turnos.service;

import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;
import com.getion.turnos.model.entity.Turn;
import com.getion.turnos.model.response.PatientResponse;
import com.getion.turnos.model.response.ScheduleResponse;
import com.getion.turnos.model.response.TurnResponse;
import com.getion.turnos.repository.ScheduleRepository;
import com.getion.turnos.service.injectionDependency.HealthCenterService;
import com.getion.turnos.service.injectionDependency.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final HealthCenterService healthCenterService;

    @Override
    public HealthCenterEntity finByName(String centerName) {

        return null;
    }

    @Override
    public Page<ScheduleResponse> getPaginatedSchedule(int page, int size, String centerName, Date startDate, Date endDate) {
        Pageable pageable = PageRequest.of(page, size);
        HealthCenterEntity healthCenter = healthCenterService.finByName(centerName);
        Schedule schedule = healthCenter.getSchedule();


        return null;
    }


}
