package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.request.PatientRequest;
import com.getion.turnos.model.response.PatientPageResponse;
import com.getion.turnos.model.response.PatientResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<PatientResponse> mapToListPatients(List<Patient> patients) {
        return patients.stream().map(this::mapToPatientResponse).collect(Collectors.toList());
    }

    private PatientResponse mapToPatientResponse(Patient patient) {
        return PatientResponse.builder()
                .name(patient.getName())
                .surname(patient.getSurname())
                .age(patient.getAge())
                .dni(patient.getDni())
                .healthInsurance(patient.getHealthInsurance())
                .build();
    }

    public PatientPageResponse mapToPatientPage(Patient patient) {
        return PatientPageResponse.builder()
                .id(patient.getId())
                .dni(patient.getDni())
                .email(patient.getEmail())
                .genre(patient.getGenre())
                .address(patient.getAddress())
                .healthInsurance(patient.getHealthInsurance())
                .name(patient.getName())
                .surname(patient.getSurname())
                .affiliateNumber(patient.getAffiliateNumber())
                .age(patient.getAge())
                .cellphone(patient.getCellphone())
                .dateOfBirth(patient.getDateOfBirth())
                .landline(patient.getLandline())
                .nationality(patient.getNationality())
                .plan(patient.getPlan())
                .profession(patient.getProfession())
                .province(patient.getProvince())
                .build();
    }

    public void mapToPatient(PatientRequest request, Patient patient) {
        patient.setAge(request.getAge());
        patient.setAddress(request.getAddress());
        patient.setDni(request.getDni());
        patient.setAffiliateNumber(request.getAffiliateNumber());
        patient.setCellphone(request.getCellphone());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setEmail(request.getEmail());
        patient.setGenre(request.getGenre());
        patient.setLandline(request.getLandline());
        patient.setName(request.getName());
        patient.setNationality(request.getNationality());
        patient.setPlan(request.getPlan());
        patient.setProfession(request.getProfession());
        patient.setProvince(request.getProvince());
        patient.setSurname(request.getSurname());

    }
}
