package com.getion.turnos.service;

import com.getion.turnos.enums.ShiftStatus;
import com.getion.turnos.exception.HealthCenterNotFoundException;
import com.getion.turnos.exception.PatientNotFoundException;
import com.getion.turnos.exception.TurnConflictException;
import com.getion.turnos.exception.TurnNotFoundException;
import com.getion.turnos.mapper.TurnMapper;
import com.getion.turnos.model.entity.HealthCenterEntity;
import com.getion.turnos.model.entity.Patient;
import com.getion.turnos.model.entity.Turn;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.response.TurnResponse;
import com.getion.turnos.repository.TurnRepository;
import com.getion.turnos.service.injectionDependency.HealthCenterService;
import com.getion.turnos.service.injectionDependency.PatientService;
import com.getion.turnos.service.injectionDependency.TurnService;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurnServiceImpl implements TurnService {

    private final TurnRepository turnRepository;
    private final TurnMapper turnMapper;
    private final HealthCenterService healthCenterService;
    private final PatientService patientService;
    private final UserService userService;

    @Transactional
    @Override
    public void createPatientTurn(String centerName, LocalDate date, String hour, String dni, Long userId) {
        UserEntity user = userService.findById(userId);
        // Buscar el centro por nombre dentro del conjunto del usuario
        Optional<HealthCenterEntity> healthCenterOptional = user.getCenters().stream()
                .filter(center -> center.getName().equalsIgnoreCase(centerName))
                .findFirst();
        if (healthCenterOptional.isPresent()) {
            HealthCenterEntity healthCenter = healthCenterOptional.get();
            // Obtener el paciente del usuario por DNI
            Optional<Patient> patientOptional = user.getPatientSet().stream()
                    .filter(patient -> patient.getDni().equals(dni))
                    .findFirst();
            // Verificar si el paciente está asociado al usuario
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
                // Agregar el paciente al centro de salud
                healthCenter.addPatient2(patient);
                //healthCenterService.save(healthCenter);
                // Crear el turno
                Turn turn = new Turn();
                turn.setDate(date);
                turn.setHour(hour);
                turn.setAvailability(false);
                turn.setCenterName(centerName);
                turn.setPatientDni(dni);
                turn.setShiftStatus(ShiftStatus.ASIGNADO);
                turn.setSchedule(healthCenter.getSchedule());
                turn.setPatient(patient);
                turn.setUserId(user.getId());
                // Guardar el turno
                turnRepository.save(turn);

            } else {
                // Manejar el caso donde el paciente no está asociado al usuario
                throw new PatientNotFoundException("El paciente no está asociado al usuario");
            }
        } else {
            throw new HealthCenterNotFoundException("Centro de salud no encontrado para el usuario con ID " + userId);
        }
    }
    @Override
    public List<TurnResponse> getAllTurns() {
        List<Turn> turns = turnRepository.findAll();
        return turnMapper.mapToTurnEntityList(turns);
    }

    @Override
    public List<TurnResponse> getAllTurnsByCenterName(String centerName) {
        List<Turn> turns = turnRepository.findAllByCenterName(centerName);
        return turnMapper.mapToTurnEntityList(turns);
    }

    @Override
    public TurnResponse getTurnBy(String centerName, LocalDate date, String hour) {
        Turn turn = turnRepository.findByCenterNameAndDateAndHourTurn(centerName, date, hour)
                .orElseThrow(() -> new TurnNotFoundException("Turno no encontrado"));
        TurnResponse response = turnMapper.mapToTurnResponse(turn);
        return response;
    }

    @Override
    public void deleteTurnById(Long id) {
        Turn turn = turnRepository.findById(id)
                .orElseThrow(() -> new TurnNotFoundException("Tuno no encontrado con id: " + id));
        turnRepository.delete(turn);
    }

    @Override
    public List<TurnResponse> getAllTurnsByCenterNameAndUserId(String centerName, Long userId) {
        UserEntity user = userService.findById(userId);
        // Buscar el centro por nombre dentro del conjunto del usuario
        Optional<HealthCenterEntity> healthCenter = user.getCenters().stream()
                .filter(center -> center.getName().equalsIgnoreCase(centerName))
                .findFirst();
        if(healthCenter.isPresent()){
            List<Turn> turnList = healthCenter.get().getSchedule().getTurnList();

            // Mapear los objetos Turn a TurnResponse
            List<TurnResponse> turnResponses = turnList.stream()
                    .map(turn -> turnMapper.mapToTurnResponse(turn))
                    .collect(Collectors.toList());

            return turnResponses;
        }
        else {
            throw new HealthCenterNotFoundException("Centro no encontrado para el usuario " + user.getUsername());
        }

    }

    @Override
    public List<TurnResponse> getAllTurnsByUserId(Long userId) {
        // Obtener el usuario por su ID
        UserEntity user = userService.findById(userId);

        // Obtener la lista de turnos del usuario del día actual
        List<Turn> turns = turnRepository.findByUserIdAndDate(userId, LocalDate.now());

        // Ordenar los turnos por hora ascendente
        turns.sort(Comparator.comparing(turn -> LocalTime.parse(turn.getHour(), DateTimeFormatter.ofPattern("H:mm"))));

        List<TurnResponse> responses = turnMapper.mapToTurnEntityList(turns);

        return responses;
    }

    @Transactional
    @Override
    public void changeStatus(Long turnId, String status) {
        Turn turn = turnRepository.findById(turnId).orElseThrow(
                () -> new TurnNotFoundException("Turno no encontrado con id = " + turnId)
        );
        turn.setShiftStatus(ShiftStatus.valueOf(status));
        turnRepository.save(turn);
    }
}
