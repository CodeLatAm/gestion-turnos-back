package com.getion.turnos.service;

import com.getion.turnos.exception.HealthCenterAlreadyExistException;
import com.getion.turnos.exception.HealthCenterNotFoundException;
import com.getion.turnos.exception.PatientNotFoundException;
import com.getion.turnos.mapper.HealthCenterMapper;
import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Patient;
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

    @Override
    public Integer getTotalPatientsByCenter(Long userId, String centerName){
        UserEntity user = userService.findById(userId);
        // Buscar el centro por nombre dentro del conjunto del usuario
        Optional<HealthCenterEntity> healthCenterOptional = user.getCenters().stream()
                .filter(center -> center.getName().equalsIgnoreCase(centerName))
                .findFirst();
        if(healthCenterOptional.isPresent()){
            HealthCenterEntity healthCenter = healthCenterOptional.get();
            return healthCenter.getPatientSet().size();
        }
        else {
            throw new HealthCenterNotFoundException("Centro no encontrado con nombre " + centerName);
        }
    }

    @Override
    public Integer getTotalPatientsByUser(Long userId) {
        UserEntity user = userService.findById(userId);

        return user.getCenters().size();
    }

    @Override
    public void save(HealthCenterEntity healthCenter) {
        healthCenterRepository.save(healthCenter);
    }

    /* @Override
    public void deletePatientByCenter(Long userId, String centerName, Long patientId) {
         UserEntity user = userService.findById(userId);
         Set<HealthCenterEntity> center = user.getCenters();
         Optional<HealthCenterEntity> centerOptional = center.stream()
                 .filter(c -> c.getName().equalsIgnoreCase(centerName))
                 .findFirst();
         // Verificar si se encontró el centro y si el paciente está en él
         boolean deleted = centerOptional.map(healthCenter -> {
             List<Patient> patients = healthCenter.getPatientSet();
             return patients.removeIf(p -> p.getId().equals(patientId));
         }).orElse(false);
         // Si el paciente no se encontró, lanzar excepción
         if (!deleted) {
             throw new PatientNotFoundException("Paciente no encontrado en el centro especificado");
         }

         // Guardar cambios en la base de datos
         centerOptional.ifPresent(healthCenterRepository::save);
     }*/
    @Override
    public void deletePatientByCenter(Long userId, String centerName, Long patientId) {
        UserEntity user = userService.findById(userId);
        Set<HealthCenterEntity> centers = user.getCenters();
        //Verificamos si el centerName esta en los centros del usuario
        Optional<HealthCenterEntity> center = centers.stream()
                .filter(c -> c.getName().equalsIgnoreCase(centerName)).findFirst();
        boolean deleted = center.map(healthCenter -> {
            List<Patient> patients = healthCenter.getPatientSet();
            return patients.removeIf(patient -> patient.getId().equals(patientId));
        }).orElse(false);
        if(!deleted){
            throw new PatientNotFoundException("Paciente no encontrado en el centro especificado");
        }
        center.ifPresent(healthCenterRepository::save);
    }
}
