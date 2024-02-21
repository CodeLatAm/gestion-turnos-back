package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.ClinicHistory;
import com.getion.turnos.model.request.ClinicHistoryRequest;
import org.springframework.stereotype.Component;

@Component
public class ClinicHistoryMapper {
    public ClinicHistory mapTo(ClinicHistoryRequest request) {
        return ClinicHistory.builder()
                .background(request.getBackground())
                .centerName(request.getCenterName())
                .localDate(request.getLocalDate())
                .reasonForConsultation(request.getReasonForConsultation())
                .build();
    }
}
