package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.BusinessHours;
import com.getion.turnos.model.request.BusinessHoursRequest;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class BusinessHoursMapper {

    public BusinessHours mapToBusinessHourRequest(BusinessHoursRequest request) {
        return BusinessHours.builder()
                .centerName(request.getCenterName())
                .day(request.getDay())
                .startTime(LocalTime.parse(request.getStartTime()))
                .endTime(LocalTime.parse(request.getEndTime()))
                .build();
    }
}
