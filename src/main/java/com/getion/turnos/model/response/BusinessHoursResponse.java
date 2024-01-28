package com.getion.turnos.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessHoursResponse {

    private Long id;
    private String centerName;
    private String day;
    private String startTime;
    private String endTime;

}
