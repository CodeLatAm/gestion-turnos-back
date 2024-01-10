package com.getion.turnos.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessHoursResponse {

    private Long id;
    private String centerName;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;

}
