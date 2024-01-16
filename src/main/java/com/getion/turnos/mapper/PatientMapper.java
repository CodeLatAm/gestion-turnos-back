package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.request.PatientRequest;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient mapToPatientRequest(PatientRequest request) {
        return Patient.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .healthInsurance(request.getHealthInsurance())
                .genre(request.getGenre())
                .plan(request.getPlan())
                .affiliateNumber(request.getAffiliateNumber())
                .dateOfBirth(request.getDateOfBirth())
                .cellphone(request.getCellphone())
                .landline(request.getLandline())
                .nationality(request.getNationality())
                .province(request.getProvince())
                .address(request.getAddress())
                .profession(request.getProfession())
                .email(request.getEmail())
                .dni(request.getDni())
                .build();
    }
}
