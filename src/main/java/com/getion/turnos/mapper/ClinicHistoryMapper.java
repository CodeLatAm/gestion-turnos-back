package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.ClinicHistory;
import com.getion.turnos.model.request.ClinicHistoryRequest;
import com.getion.turnos.model.response.ClinicHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClinicHistoryMapper {

    private final PatientMapper patientMapper;
    public ClinicHistory mapTo(ClinicHistoryRequest request) {
        return ClinicHistory.builder()
                .background(request.getBackground())
                .centerName(request.getCenterName())
                .localDate(request.getLocalDate())
                .reasonForConsultation(request.getReasonForConsultation())
                .observations(request.getObservations())
                .complementaryStudies(request.getComplementaryStudies())
                .physicalExam(request.getPhysicalExam())
                .build();
    }

    public List<ClinicHistoryResponse> mapToList(List<ClinicHistory> clinicHistoryList) {
        return clinicHistoryList.stream().map(this::mapToClinicResponse)
                .collect(Collectors.toList());
    }

    private ClinicHistoryResponse mapToClinicResponse(ClinicHistory history) {
        return ClinicHistoryResponse.builder()
                .id(history.getId())
                .background(history.getBackground())
                .centerName(history.getCenterName())
                .complementaryStudies(history.getComplementaryStudies())
                .localDate(history.getLocalDate())
                .observations(history.getObservations())
                .reasonForConsultation(history.getReasonForConsultation())
                .patientResponse(patientMapper.mapToPatientPage(history.getPatient()))
                .build();
    }
}
