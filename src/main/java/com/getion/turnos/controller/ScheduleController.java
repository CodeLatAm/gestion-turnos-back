package com.getion.turnos.controller;

import com.getion.turnos.model.response.ScheduleResponse;
import com.getion.turnos.service.injectionDependency.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/paged")
    public ResponseEntity<Page<ScheduleResponse>> paginationDates(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "7") int size,
            @RequestParam(name = "centerName") String centerName,
            @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate)
    {
        Page<ScheduleResponse> responses = scheduleService.getPaginatedSchedule(page,size,centerName,startDate,endDate);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
