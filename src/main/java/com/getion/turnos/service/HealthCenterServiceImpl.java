package com.getion.turnos.service;

import com.getion.turnos.exception.HealthCenterAlreadyExistException;
import com.getion.turnos.exception.HealthCenterNotFoundException;
import com.getion.turnos.mapper.HealthCenterMapper;
import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.HealthCenterRequest;
import com.getion.turnos.model.response.HealthCenterNamesResponse;
import com.getion.turnos.repository.HealthCenterRepository;
import com.getion.turnos.repository.ScheduleRepository;
import com.getion.turnos.service.injectionDependency.HealthCenterService;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class HealthCenterServiceImpl implements HealthCenterService {

    private final HealthCenterRepository healthCenterRepository;
    private final UserService userService;
    private final HealthCenterMapper healthCenterMapper;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    @Override
    public void createCenter(Long id, HealthCenterRequest request) {
        UserEntity user = userService.findById(id);
        System.out.println("Usuario recuperado: " + user);

        HealthCenterEntity healthCenter = healthCenterMapper.mapToHealthCenterRequest(request);
        Schedule schedule = new Schedule();

        scheduleRepository.save(schedule);
        /*Optional<HealthCenterEntity> existingHealthCenter = healthCenterRepository.findByName(healthCenter.getName());
        if (existingHealthCenter.isPresent()) {
            System.out.println("Centro de salud existente encontrado: " + existingHealthCenter.get().getName());
            throw new HealthCenterAlreadyExistException("Este centro ya existe.");
        }*/
        Optional<HealthCenterEntity> center = healthCenterRepository.findByNameAndUserEntity(healthCenter.getName(), user);
        if (center.isPresent()) {
            log.error("Centro de salud existente encontrado: " + center.get().getName() + "para este usuario");
            throw new HealthCenterAlreadyExistException("Este centro ya existe.");
        }
        healthCenter.setUserEntity(user);
        healthCenter.setSchedule(schedule);
        schedule.setHealthCenter(healthCenter);
        user.addCenter(healthCenter);
        healthCenterRepository.save(healthCenter);
    }

    public static List<String> generarFechasPorSemana(LocalDate fechaInicio, LocalDate fechaFin) {
        List<String> fechasFormateadas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d/MM/yyyy");

        LocalDate fechaActual = fechaInicio;

        while (!fechaActual.isAfter(fechaFin)) {
            String fechaFormateada = fechaActual.format(formatter);
            fechasFormateadas.add(fechaFormateada);

            // Avanza al siguiente día
            fechaActual = fechaActual.plusDays(1);
        }

        return fechasFormateadas;
    }

    @Override
    public HealthCenterEntity finByName(String centerName) {
        Optional<HealthCenterEntity> center = healthCenterRepository.findByName(centerName);
        if(center.isEmpty()){
            log.error("Centro no encontrado: ", centerName );
            throw new HealthCenterNotFoundException("Centro no encontrado: " + centerName );
        }
        return center.get();
    }

    @Override
    public Set<HealthCenterNamesResponse> getAllCentersName(Long userId) {
        UserEntity user = userService.findById(userId);
        Set<HealthCenterNamesResponse> responses = healthCenterMapper.mapToCenters(user.getCenters());
        return responses;
    }
}
