package com.getion.turnos.controller;

import com.getion.turnos.model.response.ScheduleResponse;
import com.getion.turnos.service.injectionDependency.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ResponseEntity<Page<ScheduleResponse>>
}
