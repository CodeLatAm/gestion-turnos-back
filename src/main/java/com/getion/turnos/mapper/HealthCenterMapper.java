package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Schedule;
import com.getion.turnos.model.request.HealthCenterRequest;
import com.getion.turnos.model.response.HealthCenterNamesResponse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<HealthCenterNamesResponse> mapToCenters(Set<HealthCenterEntity> centers) {
        return centers.stream().map(this::mapToCentersResponse).collect(Collectors.toSet());
    }

    private HealthCenterNamesResponse mapToCentersResponse(HealthCenterEntity healthCenterEntity) {
        return HealthCenterNamesResponse.builder()
                .name(healthCenterEntity.getName())
                .totalPatients(healthCenterEntity.getPatientSet().size())
                .build();
    }
}
