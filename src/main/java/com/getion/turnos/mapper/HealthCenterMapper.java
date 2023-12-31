package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;
import com.getion.turnos.model.request.HealthCenterRequest;
import org.springframework.stereotype.Component;

@Component
public class HealthCenterMapper {

    public HealthCenterEntity mapToHealthCenterRequest(HealthCenterRequest request) {
        return HealthCenterEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .specialty(request.getSpecialty())

                .build();
    }
}
