package com.getion.turnos.service;

import com.getion.turnos.exception.TurnConflictException;
import com.getion.turnos.mapper.TurnMapper;
import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.entity.Turn;
import com.getion.turnos.repository.TurnRepository;
import com.getion.turnos.service.injectionDependency.HealthCenterService;
import com.getion.turnos.service.injectionDependency.PatientService;
import com.getion.turnos.service.injectionDependency.TurnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnServiceImpl implements TurnService {

    private final TurnRepository turnRepository;
    private final TurnMapper turnMapper;
    private final HealthCenterService healthCenterService;
    private final PatientService patientService;

    @Override
    public void createPatientTurn(String centerName, LocalDate date, String hour, String dni) {
        // Realiza la búsqueda en la base de datos para verificar si ya existe un turno para esa fecha y hora en el centro
        List<Turn> existingTurns = turnRepository.findByCenterNameAndDateAndHour(centerName, date, hour);

        if (!existingTurns.isEmpty()) {
            // Lanza una excepción si ya existe un turno para esa fecha y hora en el centro
            throw new TurnConflictException("Ya existe un turno para la fecha y hora especificadas en este centro");
        }
        HealthCenterEntity center = healthCenterService.finByName(centerName);
        Patient patient = patientService.findByDni(dni);
        Turn createTurn = turnMapper.mapToTurnEntity(centerName, date, hour, dni);
        createTurn.setSchedule(center.getSchedule());
        createTurn.setPatient(patient);
        turnRepository.save(createTurn);

    }
}
