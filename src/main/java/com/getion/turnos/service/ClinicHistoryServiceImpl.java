package com.getion.turnos.service;

import com.getion.turnos.exception.ClinicHistoryNotFountException;
import com.getion.turnos.exception.HealthCenterNotFoundException;
import com.getion.turnos.mapper.ClinicHistoryMapper;
import com.getion.turnos.model.entity.ClinicHistory;
import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.ClinicHistoryRequest;
import com.getion.turnos.model.response.ClinicHistoryResponse;
import com.getion.turnos.model.response.PatientResponse;
import com.getion.turnos.repository.ClinicHistoryRepository;
import com.getion.turnos.service.injectionDependency.ClinicHistoryService;
import com.getion.turnos.service.injectionDependency.PatientService;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicHistoryServiceImpl implements ClinicHistoryService {

    private final ClinicHistoryRepository clinicHistoryRepository;
    private final PatientService patientService;
    private final UserService userService;
    private final ClinicHistoryMapper clinicHistoryMapper;

    @Transactional
    @Override
    public void addClinicHistory(Long patientId, Long userId, ClinicHistoryRequest request) {
        UserEntity user = userService.findById(userId);
        // Verificar si existe un centro con el nombre proporcionado en la solicitud
        boolean centroExiste = user.getCenters().stream()
                .anyMatch(centro -> centro.getName().equals(request.getCenterName()));
        if(!centroExiste){
            throw new HealthCenterNotFoundException("El centro no existe " + request.getCenterName());
        }
        Patient patient = patientService.findByIdAndUser(patientId, user);
        ClinicHistory history = clinicHistoryMapper.mapTo(request);
        patient.addClinicHistory(history);
        clinicHistoryRepository.save(history);
    }

    @Override
    public List<ClinicHistoryResponse> getAllClinicHistory(Long patientId, String centerName) {
        Patient patient = patientService.findById(patientId);
        List<ClinicHistory> clinicHistoryList = clinicHistoryRepository.findByPatientIdAndCenterNameOrderByLocalDateDesc(patientId, centerName);
        List<ClinicHistoryResponse> responses = clinicHistoryMapper.mapToList(clinicHistoryList);
        return responses;
    }
    @Override
    public void deletedClinicHistory(Long id) {
        ClinicHistory clinicHistory = clinicHistoryRepository.findById(id).orElseThrow(() -> {
            throw new ClinicHistoryNotFountException("Historia clinica no encontrada con id: " + id);
        });
        clinicHistoryRepository.delete(clinicHistory);
    }
}
