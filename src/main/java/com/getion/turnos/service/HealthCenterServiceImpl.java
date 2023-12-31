package com.getion.turnos.service;

import com.getion.turnos.exception.HealthCenterAlreadyExistException;
import com.getion.turnos.mapper.HealthCenterMapper;
import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.HealthCenterRequest;
import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.repository.HealthCenterRepository;
import com.getion.turnos.service.injectionDependency.HealthCenterService;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class HealthCenterServiceImpl implements HealthCenterService {

    private final HealthCenterRepository healthCenterRepository;
    private final UserService userService;
    private final HealthCenterMapper healthCenterMapper;

    @Transactional
    @Override
    public void createCenter(Long id, HealthCenterRequest request) {
        UserEntity user = userService.findById(id);

        HealthCenterEntity healthCenter = healthCenterMapper.mapToHealthCenterRequest(request);
        Schedule schedule = new Schedule();
        healthCenter.setUserEntity(user);
        healthCenter.setSchedule(schedule);
        schedule.setHealthCenter(healthCenter);
        // Verificar si el HealthCenter ya existe en la base de datos por nombre y dirección
        Optional<HealthCenterEntity> existingHealthCenter = healthCenterRepository.findByNameAndAddress(
                healthCenter.getName(),
                healthCenter.getAddress()
        );

        if (existingHealthCenter.isPresent()) {
            throw new HealthCenterAlreadyExistException("Este centro ya existe.");
        }
        user.addCenter(healthCenter);
        healthCenterRepository.save(healthCenter);
    }
}
