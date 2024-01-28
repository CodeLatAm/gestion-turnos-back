package com.getion.turnos.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {

    private Long id;
    private Date date;
    private List<TurnResponse> turnResponseList;

}
