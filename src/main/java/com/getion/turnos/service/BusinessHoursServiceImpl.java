package com.getion.turnos.service;

import com.getion.turnos.exception.BusinessHourAlreadyInUseException;
import com.getion.turnos.exception.HealthCenterNotFoundException;
import com.getion.turnos.mapper.BusinessHoursMapper;
import com.getion.turnos.model.entity.BusinessHours;
import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.BusinessHoursRequest;
import com.getion.turnos.model.response.BusinessHoursResponse;
import com.getion.turnos.repository.BusinessHoursRepository;
import com.getion.turnos.service.injectionDependency.BusinessHoursService;
import com.getion.turnos.service.injectionDependency.HealthCenterService;
import com.getion.turnos.service.injectionDependency.ScheduleService;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Log4j2
@Service
@RequiredArgsConstructor
public class BusinessHoursServiceImpl implements BusinessHoursService {

    private final BusinessHoursRepository businessHoursRepository;
    //private final ScheduleService scheduleService;
    private final HealthCenterService healthCenterService;
    private final BusinessHoursMapper businessHoursMapper;
    private final UserService userService;

    @Transactional
    @Override
    public void createAttentionDays(BusinessHoursRequest request) {
        UserEntity user = userService.findById(request.getUserId());
        // Filtrar los centros del usuario por el nombre proporcionado en la solicitud
        Optional<HealthCenterEntity> optionalCenter = user.getCenters().stream()
                .filter(center -> center.getName().equals(request.getCenterName()))
                .findFirst();
        if(optionalCenter.isPresent()){
            Schedule schedule = optionalCenter.get().getSchedule();
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
        }else {
            throw new HealthCenterNotFoundException("Centro no encontrado para el usuario " + user.getUsername());
        }

    }

    @Override
    public List<BusinessHoursResponse> getAllBusinessHoursBy(String centerName) {
        HealthCenterEntity center = healthCenterService.finByName(centerName);
        List<BusinessHoursResponse> responses = businessHoursMapper.mapToBusinessHourEntityList(center.getSchedule().getBusinessHours());
        return responses;
    }

    @Override
    public Set<BusinessHoursResponse> getAllBusinessHoursByCenterAndUserIdAndDay(String centerName, Long userId, String day) {
        UserEntity user = userService.findById(userId);
        if (day.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El dia es requerido");
        }
        // Buscar el centro por nombre dentro del conjunto del usuario
        Optional<HealthCenterEntity> healthCenterOptional = user.getCenters().stream()
                .filter(center -> center.getName().equalsIgnoreCase(centerName))
                .findFirst();
        if(healthCenterOptional.isPresent()){
            HealthCenterEntity center = healthCenterOptional.get();
            // Filtrar los horarios comerciales por el día especificado
            List<BusinessHours> businessHoursByDay = center.getSchedule().getBusinessHours().stream()
                    .filter(businessHours -> businessHours.getDay().equalsIgnoreCase(day))
                    // Aquí puedes realizar cualquier filtrado adicional según sea necesario
                    .collect(Collectors.toList());

            // Mapear los objetos BusinessHours a BusinessHoursResponse
            Set<BusinessHoursResponse> responses = businessHoursByDay.stream()
                    .map(businessHours -> businessHoursMapper.mapToBusinessHourSetResponse(businessHours))
                    .collect(Collectors.toSet());

            return responses;
        }
        else {
            log.error("El centro de salud con el nombre " + centerName + " no fue encontrado para el usuario con ID " + userId);
            throw new HealthCenterNotFoundException("El centro de salud con el nombre " + centerName + " no fue encontrado para el usuario con ID " + userId);
        }
    }

    @Override
    public Set<BusinessHoursResponse> getAllBusinessHoursByCenterAndUserId(String centerName, Long userId) {
        UserEntity user = userService.findById(userId);

        // Buscar el centro por nombre dentro del conjunto del usuario
        Optional<HealthCenterEntity> healthCenterOptional = user.getCenters().stream()
                .filter(center -> center.getName().equalsIgnoreCase(centerName))
                .findFirst();
        if(healthCenterOptional.isPresent()){
            HealthCenterEntity center = healthCenterOptional.get();
            // Filtrar los horarios comerciales por el día especificado
            List<BusinessHours> businessHoursByDay = center.getSchedule().getBusinessHours();

            // Mapear los objetos BusinessHours a BusinessHoursResponse
            Set<BusinessHoursResponse> responses = businessHoursByDay.stream()
                    .map(businessHours -> businessHoursMapper.mapToBusinessHourSetResponse(businessHours))
                    .collect(Collectors.toSet());

            return responses;
        }
        else {
            log.error("El centro de salud con el nombre " + centerName + " no fue encontrado para el usuario con ID " + userId);
            throw new HealthCenterNotFoundException("El centro de salud con el nombre " + centerName + " no fue encontrado para el usuario con ID " + userId);
        }
    }

}
