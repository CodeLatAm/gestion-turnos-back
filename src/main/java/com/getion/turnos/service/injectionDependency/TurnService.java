package com.getion.turnos.service.injectionDependency;

import java.time.LocalDate;

public interface TurnService {
    void createPatientTurn(String centerName, LocalDate date, String hour, String dni);
}
