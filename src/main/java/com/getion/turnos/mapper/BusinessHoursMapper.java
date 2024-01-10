package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.BusinessHours;
import com.getion.turnos.model.request.BusinessHoursRequest;
import com.getion.turnos.model.response.BusinessHoursResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusinessHoursMapper {

    public BusinessHours mapToBusinessHourRequest(BusinessHoursRequest request) {
        return BusinessHours.builder()
                .centerName(request.getCenterName())
                .day(request.getDay())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }

    public List<BusinessHoursResponse> mapToBusinessHourEntityList(List<BusinessHours> businessHours) {
        return businessHours.stream().map(this::mapToBusinessHourResponse).collect(Collectors.toList());
    }

    private BusinessHoursResponse mapToBusinessHourResponse(BusinessHours businessHours) {
        return BusinessHoursResponse.builder()
                .id(businessHours.getId())
                .centerName(businessHours.getCenterName())
                .startTime(businessHours.getStartTime())
                .endTime(businessHours.getEndTime())
                .day(businessHours.getDay())
                .build();
    }
}