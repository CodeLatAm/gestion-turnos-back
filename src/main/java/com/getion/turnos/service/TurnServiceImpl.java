package com.getion.turnos.service;

import com.getion.turnos.enums.ShiftStatus;
import com.getion.turnos.exception.*;
import com.getion.turnos.mapper.TurnMapper;
import com.getion.turnos.model.entity.*;
import com.getion.turnos.model.response.TurnResponse;
import com.getion.turnos.repository.TurnRepository;
import com.getion.turnos.service.injectionDependency.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    //private final HealthCenterService healthCenterService;
    private final ScheduleService scheduleService;
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

    @Override
    public void deleteAllTurnsByIdPatient(Long id) {
        List<Turn> turns = turnRepository.findByPatientId(id);
        turnRepository.deleteAll(turns);
    }

    @Transactional
    @Override
    public void deletePatient(Long patientId) {
        Patient patient = patientService.findById(patientId);
        patient.deleteAllClinicHistory();
        // Obtener la lista de centros de salud asociados al paciente
        Set<HealthCenterEntity> centers = patient.getUser().getCenters();

        // Iterar sobre cada centro de salud y eliminar al paciente de su lista de pacientes
        for (HealthCenterEntity center : centers) {
            center.getPatientSet().remove(patient);
        }

        // Eliminar todos los turnos asociados al paciente
        turnRepository.deleteAllByPatientId(patientId);

        // Eliminar al paciente
        patientService.deletePatient(patient);

    }

    @Override
    public List<TurnResponse> searchTurnsByDateAndDni(String date, String dni, Long userId) {
        UserEntity user = userService.findById(userId);

        if(date.isEmpty() || dni.isEmpty()){
            throw new PersonalizedIllegalArgumentException("Los parámetros de fecha y DNI no pueden estar vacíos");
        }
        Patient patient = patientService.findByDni(dni);
        LocalDate endDate = getEndDate(date);
        LocalDate startDate = LocalDate.now();
        List<Turn> turns;
        if(date.equalsIgnoreCase("Todos")){
            turns = turnRepository.findByUserIdAndPatientDni(userId, dni);
        } else {
            // Obtener todos los turnos del usuario y DNI especificados
            turns = turnRepository.findByUserIdAndPatientDni(userId, dni);
            // Filtrar los turnos dentro del rango de fechas
            turns = filterTurnsByDate(turns, startDate, endDate);
        }

        List<TurnResponse> responses = turnMapper.mapToTurnEntityList(turns);
        return responses;
    }

    // Método para filtrar los turnos por fecha en memoria
    private List<Turn> filterTurnsByDate(List<Turn> turns, LocalDate startDate, LocalDate endDate) {
        List<Turn> filteredTurns = new ArrayList<>();
        for (Turn turn : turns) {
            // Verificar si la fecha del turno está dentro del rango especificado
            if (turn.getDate().isAfter(startDate) && turn.getDate().isBefore(endDate)) {
                filteredTurns.add(turn);
            }
        }
        return filteredTurns;
    }

    private LocalDate getEndDate(String fecha) {
        switch (fecha) {
            case "Hoy":
                return LocalDate.now();
            case "Próximos 7 días":
                return LocalDate.now().plusDays(7);
            case "Los Próximos 15 días":
                return LocalDate.now().plusDays(15);
            case "El Próximo mes":
                return LocalDate.now().plusMonths(1);
            case "El Próximo año":
                return LocalDate.now().plusYears(1);
            default:

                return LocalDate.now().plusDays(30);
        }
    }

    @Override
    public List<TurnResponse> searchTurnsByDateAndDniFilters(String date, String dni, Long userId) {
        UserEntity user = userService.findById(userId);
        if(date.isEmpty()){
            throw new PersonalizedIllegalArgumentException("El parámetro fecha no puede estar vacío");
        }
        LocalDate endDate = getEndDate(date);
        LocalDate startDate = LocalDate.now();
        List<Turn> turns;
        if(date.equalsIgnoreCase("Todos")) {
            turns = turnRepository.findByUserIdAndPatientDniStartingWith(userId, dni.substring(0, Math.min(dni.length(), 3)));
        } else {
            // Obtener todos los turnos del usuario y DNI especificados
            turns = turnRepository.findByUserIdAndPatientDniStartingWith(userId, dni.substring(0, Math.min(dni.length(), 3)));
            // Filtrar los turnos dentro del rango de fechas
            turns = filterTurnsByDate(turns, startDate, endDate);
        }
        List<TurnResponse> responses = turnMapper.mapToTurnEntityList(turns);
        return responses;

    }
}
