package com.getion.turnos.service;

import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;
import com.getion.turnos.repository.ScheduleRepository;
import com.getion.turnos.service.injectionDependency.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public HealthCenterEntity finByName(String centerName) {

        return null;
    }
}
