package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.response.TurnResponse;
import java.time.LocalDate;
import java.util.List;

public interface TurnService {
    void createPatientTurn(String centerName, LocalDate date, String hour, String dni, Long userId);

    List<TurnResponse> getAllTurns();

    List<TurnResponse> getAllTurnsByCenterName(String centerName);

    TurnResponse getTurnBy(String centerName, LocalDate localDate, String hour);

    void deleteTurnById(Long id);

    List<TurnResponse> getAllTurnsByCenterNameAndUserId(String centerName, Long userId);

    List<TurnResponse> getAllTurnsByUserId(Long userId);

    void changeStatus(Long turnId, String status);
}
